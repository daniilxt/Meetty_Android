package ru.daniilxt.feature.user_dialogs.presentation.util

import android.annotation.SuppressLint
import ru.daniilxt.common.BuildConfig
import ru.daniilxt.feature.domain.model.ChatMessage
import ru.daniilxt.feature.domain.model.User
import ru.daniilxt.feature.domain.model.UserDialog
import java.time.LocalDateTime

@SuppressLint("NewApi")
object UserDialogsProvider {
    val myUser = User(
        id = 1,
        firstName = "Igor",
        lastName = "Fedorov",
        avatarLink = "https://sun9-19.userapi.com/impf/c845416/v845416322/2ce95/URevq_jTofQ.jpg?size=1080x1080&quality=96&sign=09bee1e2e05cc02b4c32e7da609d45f4&type=album"
    )

    val firstUser = User(
        id = 2,
        firstName = "Антон",
        lastName = "Новиков",
        avatarLink = BuildConfig.ENDPOINT + "/api/v1/media/image/profile/7"
    )
/*    val firstUser = User(
        id = 2,
        firstName = "Grigoriy",
        lastName = "Tolstolobikov",
        avatarLink = "https://sun9-80.userapi.com/impf/0MmEdStrLGBnqe-ulQi3FgzQe9oagCWdrBJo8w/8MQbR702JZ8.jpg?size=1440x1920&quality=95&sign=b1c3f364b3eb122cc110ab8e5cf399a6&type=album"
    )*/

    fun getUserDialogs() = listOf(
        UserDialog(
            id = 1L,
            lastMessage = ChatMessage(
                id = 1,
                dateTime = LocalDateTime.of(2022, 4, 13, 14, 22, 55),
                "Привет, как дела?",
                reactions = emptyList(),
                sender = myUser,
                isMy = false
            ),
            firstUser = myUser,
            secondUser = firstUser
        ),
    )
/*        UserDialog(
            id = 2L,
            lastMessage = Message(
                id = 4,
                dateTime = LocalDateTime.of(2022, 4, 13, 13, 55, 55),
                "Oh, Lore Loren",
                reactions = emptyList(),
                sender = secondUser,
                isMy = false
            ),
            firstUser = secondUser,
            secondUser = myUser
        ),
        UserDialog(
            id = 3L,
            lastMessage = Message(
                id = 6,
                dateTime = LocalDateTime.of(2022, 4, 13, 13, 12, 55),
                "Welcome to Middenheim",
                reactions = emptyList(),
                sender = secondUser,
                isMy = false
            ),
            firstUser = thirdUser,
            secondUser = myUser
        )
    )*/
}
