package ru.daniilxt.feature.main_screen_user_card.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.main_screen_user_card.presentation.adapter.SwypedUserCard

class MainScreenUserCardViewModel(private val router: FeatureRouter) : BaseViewModel() {

    private val _userCards: MutableStateFlow<List<SwypedUserCard>> = MutableStateFlow(emptyList())
    val userCards: StateFlow<List<SwypedUserCard>> get() = _userCards

    init {
        _userCards.value = List(100) {
            SwypedUserCard(
                name = "Екатерина $it",
                photoUri = "https://sun9-30.userapi.com/sun9-57/s/v1/if1/5uukGklSa12cTtzRaFAB6rnxSEy0078CECF4Bt80CEJibU979WejyH1haetd5cLfqPPvliyc.jpg?size=899x1280&quality=96&type=album"
            )
        }
    }
}
