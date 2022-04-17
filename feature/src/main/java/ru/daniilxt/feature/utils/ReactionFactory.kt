package ru.daniilxt.feature.utils

import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider

object ReactionFactory {
    private fun getEmoji(): ArrayList<String> = arrayListOf(
        "\uD83D\uDE01",
        "\uD83D\uDE02",
        "\uD83E\uDD70",
        "\uD83E\uDD11",
        "\uD83E\uDD14",
        "\uD83D\uDE34",
        "\uD83E\uDD24",
        "\uD83E\uDD29",
        "\uD83D\uDE1C",
        "\uD83D\uDE1D",
        "\uD83D\uDE11",
        "\uD83E\uDD76",
        "\uD83E\uDD2F",
        "\uD83E\uDD73",
        "\uD83D\uDE2F\t",
        "\uD83D\uDE30\t",
        "\uD83D\uDE31",
        "\uD83E\uDD2C",
        "\uD83E\uDD74\t",
        "\uD83D\uDE0D",
        "\uD83D\uDE43",
        "\uD83D\uDE05",
        "\uD83E\uDDD0",
        "\uD83E\uDD75",
        "\uD83E\uDD22",
        "\uD83E\uDD23",
        "\uD83D\uDE05",
        "\uD83D\uDE37",
        "\uD83D\uDC7D",
        "\uD83E\uDD21",
        "\uD83D\uDE08",
        "\uD83D\uDC4B",
        "\uD83D\uDE03",
        "\uD83D\uDE21",
        "\uD83D\uDC4D",
        "\uD83C\uDF70",
        "\uD83C\uDF0E",
        "\uD83D\uDE80",
        "\uD83D\uDEF8",
        "\uD83C\uDF0C",
        "\uD83D\uDD25",
        "\uD83C\uDFC6",
        "\uD83C\uDF93"
    )

    fun getReactions(): List<Reaction> {
        return getEmoji().mapIndexed { index, s -> s.toReaction(index) }
    }

    private fun String.toReaction(index: Int): Reaction {
        return Reaction(
            id = index.toLong(),
            emojiText = this,
            usersId = listOf(UserDialogsProvider.myUser.id),
            count = 1
        )
    }
}
