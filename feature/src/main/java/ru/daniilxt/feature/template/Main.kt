package ru.daniilxt.feature.template

import ru.daniilxt.feature.domain.model.UserCard

fun main() {
    // GenerateFeatureModule("profile_professional_interests", "ProfileProfessionalInterests").main()
    GenerateRecyclerAdapter(
        moduleName = "main_screen",
        adapterName = "UserCard",
        dataClass = UserCard::class.java,
    ).main()
}
