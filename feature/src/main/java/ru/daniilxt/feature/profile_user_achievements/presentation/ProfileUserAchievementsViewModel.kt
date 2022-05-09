package ru.daniilxt.feature.profile_user_achievements.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.domain.model.UserAchievement

class ProfileUserAchievementsViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    private val _userAchievements: MutableStateFlow<List<UserAchievement>> = MutableStateFlow(
        emptyList()
    )
    val userAchievements: StateFlow<List<UserAchievement>> get() = _userAchievements

    fun deleteAchievement(userAchieve: UserAchievement) {
        _userAchievements.value = _userAchievements.value.filter { it != userAchieve }
    }

    fun addAchievement(achievement: UserAchievement) {
        _userAchievements.value = _userAchievements.value + listOf(achievement)
    }

    fun putUserAchievements() {
        dataWrapper.setUserAchievements(_userAchievements.value)
    }
}
