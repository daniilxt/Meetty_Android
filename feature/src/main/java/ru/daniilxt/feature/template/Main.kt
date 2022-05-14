package ru.daniilxt.feature.template

import ru.daniilxt.feature.domain.model.UserCard

fun main() {
     GenerateFeatureModule("main_screen_map", "MainScreenMap").main()
/*    GenerateRecyclerAdapter(
        moduleName = "main_screen",
        adapterName = "UserCard",
        dataClass = UserCard::class.java,
    ).main()*/
}
