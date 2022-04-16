package ru.daniilxt.feature.user_dialogs.presentation.util

import android.annotation.SuppressLint
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.User
import ru.daniilxt.feature.domain.model.UserDialog
import java.time.LocalDate
import java.time.LocalTime

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
        firstName = "Grigoriy",
        lastName = "Tolstolobikov",
        avatarLink = "https://sun9-80.userapi.com/impf/0MmEdStrLGBnqe-ulQi3FgzQe9oagCWdrBJo8w/8MQbR702JZ8.jpg?size=1440x1920&quality=95&sign=b1c3f364b3eb122cc110ab8e5cf399a6&type=album"
    )

    val secondUser = User(
        id = 3,
        firstName = "Anton",
        lastName = "Terentev",
        avatarLink = "https://sun9-50.userapi.com/impf/JkYeszfPdl2grFbELqS6sBC-8VAk2GEmriF2VQ/jfGHjCUcB4g.jpg?size=604x453&quality=95&sign=096c77a1184e96f5859ad292a74b13e9&type=album"
    )
    val thirdUser = User(
        id = 4,
        firstName = "Midden",
        lastName = "Heim",
        avatarLink = "http://img2.reactor.cc/pics/post/Warhammer-Fantasy-фэндомы-Warhammer-FRPG-Middenheim-6523998.jpeg"
    )

    fun getUserDialogs() = listOf(
        UserDialog(
            id = 1L,
            lastMessage = Message(
                id = 1,
                date = LocalDate.now(),
                time = LocalTime.of(14, 22, 55),
                "Hello world how are you",
                reactions = emptyList(),
                sender = firstUser
            ),
            firstUser = myUser,
            secondUser = firstUser
        ),
        UserDialog(
            id = 2L,
            lastMessage = Message(
                id = 4,
                date = LocalDate.now(),
                time = LocalTime.of(13, 55, 55),
                "Oh, Lore Loren",
                reactions = emptyList(),
                sender = secondUser
            ),
            firstUser = secondUser,
            secondUser = myUser
        ),
        UserDialog(
            id = 3L,
            lastMessage = Message(
                id = 6,
                date = LocalDate.now(),
                time = LocalTime.of(13, 12, 55),
                "Welcome to Middenheim",
                reactions = emptyList(),
                sender = secondUser
            ),
            firstUser = thirdUser,
            secondUser = myUser
        )
    )
}
