package ru.daniilxt.feature.main_screen_user_card.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.model.SwipedUserCard
import ru.daniilxt.feature.domain.model.UserAdditionalInfo
import ru.daniilxt.feature.domain.model.UserCategory
import java.time.LocalDate

class MainScreenUserCardViewModel(private val router: FeatureRouter) : BaseViewModel() {

    private val _userCards: MutableStateFlow<List<SwipedUserCard>> = MutableStateFlow(emptyList())
    val userCards: StateFlow<List<SwipedUserCard>> get() = _userCards

    init {
        _userCards.value = List(10) {
            SwipedUserCard(
                name = "Екатерина $it",
               // photoUri = "https://sun9-30.userapi.com/sun9-57/s/v1/if1/5uukGklSa12cTtzRaFAB6rnxSEy0078CECF4Bt80CEJibU979WejyH1haetd5cLfqPPvliyc.jpg?size=899x1280&quality=96&type=album",
                photoUri = "https://sun9-north.userapi.com/sun9-80/s/v1/if2/y_DZBDRCC2xPob8VTjNCLTDwXSV1L2FKFZK__9AViN6bi8RVma3Ls62eQ4j7LAe9n7xDcTYV02A-D5NK2eI2fT34.jpg?size=1440x1920&quality=95&type=album",
                userAdditionalInfo = UserAdditionalInfo(
                    id = it.toLong(),
                    LocalDate.of(2000 + it, 1, 1),
                    categories = listOf(
                        UserCategory(it.toLong(), "Бизнес $it"),
                        UserCategory(it.toLong(), "Прорграммирование $it"),
                        UserCategory(it.toLong(), "Мвшиностроение $it")
                    )
                )
            )
        }
    }
}
