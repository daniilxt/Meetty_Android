package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.ReactionWrapper
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider
import timber.log.Timber
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.time.LocalDateTime

@SuppressLint("NewApi")
class UserChatViewModel(private val router: FeatureRouter) : BaseViewModel() {
    private var _userMessages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    val userMessages: StateFlow<List<Message>> get() = _userMessages

    private var _userDialog: UserDialog? = null
    val userDialog get() = _userDialog!!
    fun userDialog(chat: UserDialog) {
        _userDialog = chat
    }

    private val gson: Gson = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        GsonLocalDateTimeAdapter()
    ).create()
    private var mStompClient: StompClient? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        _userMessages.value = MessageTestProvider.getMessages()
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL)
            .withServerHeartbeat(30000)
        resetSubscriptions()
        initChat()
    }

    private fun initChat() {
        resetSubscriptions()

        if (mStompClient != null) {
            val topicSubscribe = mStompClient!!.topic(CHAT_TOPIC)
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { topicMessage: StompMessage ->
                        Timber.tag(STOMP).i(topicMessage.payload)
                        val message: ChatSocketMessage =
                            gson.fromJson(topicMessage.payload, ChatSocketMessage::class.java)
                        addMessage(message.toMessage())
                    },
                    {
                        Timber.tag(STOMP).i("Error! $it")
                    }
                )

            val lifecycleSubscribe = mStompClient!!.lifecycle()
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent: LifecycleEvent ->
                    when (lifecycleEvent.type!!) {
                        LifecycleEvent.Type.OPENED -> Timber.tag(STOMP).i("Stomp connection opened")
                        LifecycleEvent.Type.ERROR -> Timber.tag(STOMP)
                            .e("Error ${lifecycleEvent.exception}")
                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT,
                        LifecycleEvent.Type.CLOSED -> {
                            Timber.tag(STOMP).i("Stomp connection closed")
                        }
                    }
                }

            compositeDisposable!!.add(lifecycleSubscribe)
            compositeDisposable!!.add(topicSubscribe)

            if (!mStompClient!!.isConnected) {
                mStompClient!!.connect()
            }
        } else {
            Timber.tag(STOMP).e("mStompClient is null!")
        }
    }

    private fun addMessage(message: Message) {
        Timber.tag(STOMP).i("added msg $message")
        _userMessages.value = _userMessages.value + listOf(message)
    }

    private fun sendCompletable(request: Completable) {
        compositeDisposable?.add(
            request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Timber.tag(STOMP).i("Stomp sended")
                    },
                    {
                        Timber.tag(STOMP).e("Stomp error $it")
                    }
                )
        )
    }

    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }

        compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()

        mStompClient?.disconnect()
        compositeDisposable?.dispose()
    }

    fun sendMessage(text: String) {
        val chatSocketMessage =
            ChatSocketMessage(
                content = text,
                sender = iUser,
                receiver = companion
            )
        sendCompletable(mStompClient!!.send(CHAT_LINK_SOCKET, gson.toJson(chatSocketMessage)))
        addMessage(chatSocketMessage.toMessage())
    }

    // TODO handle reactions count
    fun setReaction(reactionWrapper: ReactionWrapper) {
        _userMessages.value = _userMessages.value.map {
            if (it.id == reactionWrapper.messageId) {
                val reactions = it.reactions + listOf(
                    reactionWrapper.reaction
                )
                it.copy(reactions = reactions)
            } else {
                it
            }
        }
    }

    companion object {
        val iUser = UserDialogsProvider.myUser
        val companion = UserDialogsProvider.firstUser

        private const val STOMP = "STOMP_STOMP"
        const val SOCKET_URL = "wss://4847-95-24-226-149.eu.ngrok.io/api/chat/websocket"
        const val CHAT_TOPIC = "/topic/chat"
        const val CHAT_LINK_SOCKET = "/api/chat/sock"
    }
}
