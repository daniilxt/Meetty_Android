/*
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
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider
import timber.log.Timber
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@SuppressLint("NewApi")
class UserChatViewModel2(private val router: FeatureRouter) : BaseViewModel() {
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

    private var _chatState = MutableStateFlow<Message?>(null)
    val liveChatState: StateFlow<Message?> get() = _chatState

    init {
//            val headerMap: Map<String, String> =
//                Collections.singletonMap("Authorization", "Token")
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL*/
/*, headerMap*//*
)
            .withServerHeartbeat(30000)
        resetSubscriptions()
        initChat()
    }

    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
        compositeDisposable = CompositeDisposable()
    }


    private fun initChat() {
        resetSubscriptions()

        if (mStompClient != null) {
            val topicSubscribe = mStompClient!!.topic(CHAT_TOPIC)
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage: StompMessage ->
                    Timber.tag(STOMP).i("??? ${topicMessage.payload}")
                    */
/*           val message: Message =
                                   gson.fromJson(topicMessage.payload, Message::class.java)
                               addMessage(message)*//*

                },
                    {
                    }
                )

            val lifecycleSubscribe = mStompClient!!.lifecycle()
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent: LifecycleEvent ->
                    when (lifecycleEvent.type!!) {
                        LifecycleEvent.Type.OPENED -> Timber.tag(STOMP).i("Stomp connection opened")
                        LifecycleEvent.Type.ERROR -> Timber.tag(STOMP)
                            .i("Error ${lifecycleEvent.exception}")
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
            Timber.tag(STOMP).i("Stomp client is null")
        }
    }

    fun sendMessage(text: String) {
        //val message = Message(text = text, author = "Me")
        val chatSocketMessage = Message(
            id = 0,
            date = LocalDate.now(),
            time = LocalTime.now(),
            content = "hi ${LocalDateTime.now()}",
            reactions = emptyList(),
            sender = UserDialogsProvider.myUser
        )
        val test = "Hello ${LocalDateTime.now()}"
        //sendCompletable(mStompClient!!.send(CHAT_LINK_SOCKET, gson.toJson(chatSocketMessage)))
        //addMessage(chatSocketMessage)
        sendCompletable(mStompClient!!.send(CHAT_LINK_SOCKET, gson.toJson(test)))
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
                        Timber.tag(STOMP).i("Stomp error $it")
                    }
                )
        )
    }

    private fun addMessage(message: Message) {
        _chatState.value = message
    }

    companion object {
        private const val STOMP = "TAG_STOMP"
        const val SOCKET_URL = "wss://bc9b-95-24-226-149.eu.ngrok.io/api/chat/websocket"
        const val CHAT_TOPIC = "/topic/chat"
        const val CHAT_LINK_SOCKET = "/api/chat/sock"
    }

}
*/
