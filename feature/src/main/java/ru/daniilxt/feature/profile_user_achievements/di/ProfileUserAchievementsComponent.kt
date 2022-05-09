package ru.daniilxt.feature.profile_user_achievements.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.profile_user_achievements.presentation.ProfileUserAchievementsFragment

@Subcomponent(
    modules = [
        ProfileUserAchievementsModule::class,
    ]
)
@ScreenScope
interface ProfileUserAchievementsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileUserAchievementsComponent
    }

    fun inject(profileUserAchievementsFragment: ProfileUserAchievementsFragment)
}
