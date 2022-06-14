package ru.daniilxt.feature.template

import ru.daniilxt.feature.domain.model.UserCard

fun main() {
//    GenerateFeatureModule("user_profile", "UserProfile").main()
    GenerateRecyclerAdapter(
        moduleName = "main_screen_map",
        adapterName = "EduUserCard",
        dataClass = UserCard::class.java,
    ).main()
}
