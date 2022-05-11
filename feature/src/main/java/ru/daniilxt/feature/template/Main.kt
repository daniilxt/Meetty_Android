package ru.daniilxt.feature.template

import ru.daniilxt.feature.domain.model.PhotoAttach

fun main() {
    // GenerateFeatureModule("profile_professional_interests", "ProfileProfessionalInterests").main()
    GenerateRecyclerAdapter(
        moduleName = "test",
        adapterName = "UserTest",
        dataClass = PhotoAttach::class.java,
    ).main()
}
