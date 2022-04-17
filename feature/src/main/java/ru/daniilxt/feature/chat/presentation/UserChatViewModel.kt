package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import android.util.Log
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

    companion object {
        private const val STOMP = "STOMP_STOMP"
        const val SOCKET_URL = "wss://bc9b-95-24-226-149.eu.ngrok.io/api/chat/websocket"
        const val CHAT_TOPIC = "/topic/chat"
        const val CHAT_LINK_SOCKET = "/api/chat/sock"
    }

    private val gson: Gson = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        GsonLocalDateTimeAdapter()
    ).create()
    private var mStompClient: StompClient? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        _userMessages.value = MessageTestProvider.getMessages()
//            val headerMap: Map<String, String> =
//                Collections.singletonMap("Authorization", "Token")
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL/*, headerMap*/)
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
                        Log.d(STOMP, topicMessage.payload)
                        val message: ChatSocketMessage =
                            gson.fromJson(topicMessage.payload, ChatSocketMessage::class.java)
                        val newMessage = dtoToEntity(message)
                        addMessage(newMessage)
                    },
                    {
                        Log.e(STOMP, "Error!", it)
                    }
                )

            val lifecycleSubscribe = mStompClient!!.lifecycle()
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent: LifecycleEvent ->
                    when (lifecycleEvent.type!!) {
                        LifecycleEvent.Type.OPENED -> Log.d(STOMP, "Stomp connection opened")
                        LifecycleEvent.Type.ERROR -> Log.e(STOMP, "Error", lifecycleEvent.exception)
                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT,
                        LifecycleEvent.Type.CLOSED -> {
                            Log.d(STOMP, "Stomp connection closed")
                        }
                    }
                }

            compositeDisposable!!.add(lifecycleSubscribe)
            compositeDisposable!!.add(topicSubscribe)

            if (!mStompClient!!.isConnected) {
                mStompClient!!.connect()
            }
        } else {
            Log.e(STOMP, "mStompClient is null!")
        }
    }

    fun sendMessage(text: String) {
        val message = Message2(text = text, author = "Me", receiver = "Max")
        val chatSocketMessage = entityToDto(message)
        sendCompletable(mStompClient!!.send(CHAT_LINK_SOCKET, gson.toJson(chatSocketMessage)))
        addMessage(message)
    }

    private fun addMessage(message: Message2) {
        Timber.tag(STOMP).i("added msg $message")
    }

    private fun sendCompletable(request: Completable) {
        compositeDisposable?.add(
            request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(STOMP, "Stomp sended")
                    },
                    {
                        Log.e(STOMP, "Stomp error", it)
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

    fun sendMessage2(text: String) {
        _userMessages.value = _userMessages.value + listOf(
            Message(
                dateTime = LocalDateTime.now(),
                reactions = emptyList(),
                content = text,
                isMy = true,
                sender = UserDialogsProvider.myUser
            )
        )
    }

    // TODO handle reactions count
    fun setReaction(reactionWrapper: ReactionWrapper) {
        _userMessages.value = _userMessages.value.map {
            if (it.id == reactionWrapper.messageId) {
                val reactions = it.reactions + listOf(reactionWrapper.reaction)
                it.copy(reactions = reactions)
            } else {
                it
            }
        }
    }
}
