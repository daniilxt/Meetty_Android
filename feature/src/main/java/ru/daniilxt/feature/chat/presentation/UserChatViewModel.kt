package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.BuildConfig.ENDPOINT
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.ChatMessage
import ru.daniilxt.feature.domain.model.ReactionWrapper
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.domain.usecase.GetDialogMessagesUseCase
import timber.log.Timber
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.time.LocalDateTime
import java.util.*

@SuppressLint("NewApi")
class UserChatViewModel(
    private val router: FeatureRouter,
    private val tokenRepository: TokenRepository,
    private val getDialogMessagesUseCase: GetDialogMessagesUseCase
) : BaseViewModel() {
    private var _userMessages: MutableStateFlow<List<ChatMessage>> = MutableStateFlow(emptyList())
    val userMessages: StateFlow<List<ChatMessage>> get() = _userMessages

    private var _userDialog: UserDialog? = null
    val userDialog get() = _userDialog!!

    var myId: Long = -1

    fun setUserDialog(chat: UserDialog) {
        _userDialog = chat
    }

    private val gson: Gson = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        GsonLocalDateTimeAdapter()
    ).create()
    private var mStompClient: StompClient? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        val headerMap: Map<String, String> =
            Collections.singletonMap(AUTHORIZATION, BEARER + tokenRepository.getToken())
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL, headerMap)
        resetSubscriptions()
        initChat()
    }

    fun getPastMessages() {
        setEventState(ResponseState.Success)
        getDialogMessagesUseCase.invoke(userDialog.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        setEventState(ResponseState.Success)
                        _userMessages.value = it.data
                    }
                    is RequestResult.Error -> {
                    }
                }
            }, {
            }).addTo(disposable)
    }

    private fun initChat() {
        resetSubscriptions()

        if (mStompClient != null) {
            val topicSubscribe = mStompClient!!.topic(CHAT_TOPIC)
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { topicMessage: StompMessage ->
                        Timber.tag(STOMP).i("$topicMessage")
                        val message: ChatSocketMessage =
                            gson.fromJson(topicMessage.payload, ChatSocketMessage::class.java)
                        addMessage(message.toMessage(tokenRepository.getCurrentUserId()))
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
                            initChat()
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

    private fun addMessage(message: ChatMessage) {
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
                sender = userDialog.returnMyUser(myId),
                receiver = userDialog.returnCompanionUser(myId)
            )
        sendCompletable(mStompClient!!.send(CHAT_LINK_SOCKET, gson.toJson(chatSocketMessage)))
        addMessage(chatSocketMessage.toMessage(tokenRepository.getCurrentUserId()))
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
        private const val BEARER = "Bearer "
        private const val AUTHORIZATION = "Authorization"

        private const val STOMP = "STOMP_STOMP"
        val SOCKET_URL = "wss://${ENDPOINT.getAddress()}/api/v1/chat/websocket"
        const val CHAT_TOPIC = "/user/topic/chat"
        const val CHAT_LINK_SOCKET = "/api/v1/chat/sock"
    }
}

private fun String.getAddress() = replaceBeforeLast("/", "")
