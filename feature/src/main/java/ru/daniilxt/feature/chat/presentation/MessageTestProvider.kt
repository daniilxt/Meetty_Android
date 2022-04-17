package ru.daniilxt.feature.chat.presentation

import android.annotation.SuppressLint
import ru.daniilxt.feature.domain.model.Message
import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider
import java.time.LocalDateTime

@SuppressLint("NewApi")

object MessageTestProvider {

    fun getMessages(): List<Message> {
        return listOf(
            Message(
                id = 1,
                dateTime = LocalDateTime.of(2022, 4, 13, 14, 22, 55),
                "Hello world how are you",
                reactions = emptyList(),
                sender = UserDialogsProvider.firstUser,
                isMy = false
            ),
            Message(
                id = 2,
                dateTime = LocalDateTime.of(2022, 4, 13, 14, 25, 15),
                "Loren is my len the rulesd fear sealed class dexter oxcel ",
                reactions = listOf(
                    Reaction(
                        id = 1,
                        emojiText = "\uD83D\uDE34",
                        usersId = listOf(1),
                        count = 1
                    ),
                    Reaction(
                        id = 2,
                        emojiText = "\uD83D\uDE01",
                        usersId = listOf(1, 2),
                        count = 2
                    )
                ),
                sender = UserDialogsProvider.firstUser,
                isMy = false
            ),
            Message(
                id = 3,
                dateTime = LocalDateTime.of(2022, 4, 13, 15, 25, 15),
                "Loren is my len the rulesd fear sealed class dexter oxcel ",
                reactions = listOf(
                    Reaction(
                        id = 1,
                        emojiText = "\uD83D\uDE34",
                        usersId = listOf(1),
                        count = 1
                    ),
                    Reaction(
                        id = 2,
                        emojiText = "\uD83D\uDE01",
                        usersId = listOf(1, 2),
                        count = 2
                    ),
                    Reaction(
                        id = 3,
                        emojiText = "\uD83D\uDE1C",
                        usersId = listOf(1, 2),
                        count = 2
                    ),
                    Reaction(
                        id = 4,
                        emojiText = "\uD83D\uDE1D",
                        usersId = listOf(1, 2),
                        count = 2
                    ),
                    Reaction(
                        id = 5,
                        emojiText = "\uD83D\uDE11",
                        usersId = listOf(1, 2),
                        count = 2
                    ),
                    Reaction(
                        id = 6,
                        emojiText = "\uD83E\uDD22",
                        usersId = listOf(1, 2),
                        count = 2
                    ),
                ),
                sender = UserDialogsProvider.firstUser,
                isMy = false
            ),
            Message(
                id = 4,
                dateTime = LocalDateTime.of(2022, 4, 13, 15, 25, 15),
                "Loren is my len the rulesd fear sealed class dexter oxcel ",
                reactions = listOf(
                    Reaction(
                        id = 1,
                        emojiText = "\uD83D\uDE34",
                        usersId = listOf(1),
                        count = 1
                    ),
                    Reaction(
                        id = 2,
                        emojiText = "\uD83D\uDE01",

                        usersId = listOf(1, 2),
                        count = 2
                    ),
                    Reaction(
                        id = 3,
                        emojiText = "\uD83D\uDE1C",
                        usersId = listOf(1, 2),
                        count = 2
                    ),
                    Reaction(
                        id = 4,
                        emojiText = "\uD83D\uDE1D",
                        usersId = listOf(1, 2),
                        count = 2
                    )
                ),
                sender = UserDialogsProvider.firstUser,
                isMy = false
            ),
            Message(
                id = 5,
                dateTime = LocalDateTime.of(2022, 4, 13, 14, 22, 55),
                "Hello world how are you",
                reactions = emptyList(),
                sender = UserDialogsProvider.firstUser,
                isMy = false
            ),
            Message(
                id = 2,
                dateTime = LocalDateTime.of(2022, 4, 13, 14, 25, 15),
                "Loren ",
                reactions = listOf(
                    Reaction(
                        id = 1,
                        emojiText = "\uD83D\uDE34",
                        usersId = listOf(1),
                        count = 1
                    ),
                    Reaction(
                        id = 2,
                        emojiText = "\uD83D\uDE01",
                        usersId = listOf(1, 2),
                        count = 2
                    )
                ),
                sender = UserDialogsProvider.firstUser,
                isMy = false
            ),
            Message(
                id = 6,
                dateTime = LocalDateTime.of(2022, 4, 13, 14, 25, 15),
                "Message from me Hello! ",
                reactions = listOf(
                    Reaction(
                        id = 1,
                        emojiText = "\uD83D\uDE34",
                        usersId = listOf(1),
                        count = 1
                    ),
                    Reaction(
                        id = 2,
                        emojiText = "\uD83D\uDE01",
                        usersId = listOf(1, 2),
                        count = 2
                    )
                ),
                sender = UserDialogsProvider.myUser,
                isMy = true
            )
        )
    }
}
