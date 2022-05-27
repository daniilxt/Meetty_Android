package ru.daniilxt.feature.main_screen_user_list.presentation

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.SimpleUserInfo
import ru.daniilxt.feature.domain.model.UserAdditionalInfo
import ru.daniilxt.feature.domain.model.UserCard
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import java.time.LocalDate

@SuppressLint("NewApi")
class MainScreenUserListViewModel(private val router: FeatureRouter) : BaseViewModel() {

    private val _userCards: MutableStateFlow<List<UserCard>> = MutableStateFlow(emptyList())
    val userCards: StateFlow<List<UserCard>> get() = _userCards

    init {
        _userCards.value = List(100) {
            UserCard(
                id = it.toLong(),
                user = SimpleUserInfo(
                    id = it.toLong(),
                    firstName = "Екатерина $it",
                    lastName = "Иванова $it",
                    avatarUri = "https://sun9-30.userapi.com/sun9-57/s/v1/if1/5uukGklSa12cTtzRaFAB6rnxSEy0078CECF4Bt80CEJibU979WejyH1haetd5cLfqPPvliyc.jpg?size=899x1280&quality=96&type=album"
                ),
                userAdditionalInfo = UserAdditionalInfo(
                    id = it.toLong(),
                    LocalDate.of(2000 + it, 1, 1),
                    categories = listOf(
                        ProfessionalInterest(it.toLong(), "Бизнес $it"),
                        ProfessionalInterest(it.toLong(), "Прорграммирование $it"),
                        ProfessionalInterest(it.toLong(), "Мвшиностроение $it")
                    )
                )
            )
        }
    }
}
