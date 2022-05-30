package ru.daniilxt.feature.user_profile.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.UserAchievement
import java.time.LocalDate

class UserProfileViewModel(private val router: FeatureRouter) : BaseViewModel() {
    private val _userAchievements: MutableStateFlow<List<UserAchievement>> = MutableStateFlow(
        listOf(
            UserAchievement(
                title = "Junior Android Developer 2022",
                achievementDescription = "In far 2021 i graduate android cources by Tinkoff bank. I learned mach technologies like Coin, Dagger 2, MVVM, RxJava2/2",
                date = LocalDate.of(2021, 12, 11)
            ),
            UserAchievement(
                title = "Junior Android Developer 2022",
                achievementDescription = "In far 2021 i graduate android cources by Tinkoff bank. I learned mach technologies like Coin, Dagger 2, MVVM, RxJava2/2",
                date = LocalDate.of(2021, 12, 11)
            ),
            UserAchievement(
                title = "Junior Android Developer 2022",
                achievementDescription = "In far 2021 i graduate android cources by Tinkoff bank. I learned mach technologies like Coin, Dagger 2, MVVM, RxJava2/2",
                date = LocalDate.of(2021, 12, 11)
            )
        )
    )
    val userAchievements: StateFlow<List<UserAchievement>> get() = _userAchievements
    fun back() {
        router.back()
    }
}
