package ru.daniilxt.feature.main_screen_map.presentation

import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.MapEducation
import ru.daniilxt.feature.domain.model.MapSimpleUser
import ru.daniilxt.feature.domain.model.toMapSimpleUser
import ru.daniilxt.feature.domain.usecase.GetUsersInfoUseCase
import timber.log.Timber

class MainScreenMapViewModel(
    private val router: FeatureRouter,
    private val getUsersInfoUseCase: GetUsersInfoUseCase
) : BaseViewModel() {
    private val _mapEduList: MutableStateFlow<List<MapEducation>> =
        MutableStateFlow(emptyList())
    val mapEduList: StateFlow<List<MapEducation>> get() = _mapEduList

    private val _eduUsersCard: MutableStateFlow<List<MapSimpleUser>> = MutableStateFlow(emptyList())
    val eduUsersCard: StateFlow<List<MapSimpleUser>> get() = _eduUsersCard

    init {
        loadUsers()
    }

    fun loadUsers() {
        getUsersInfoUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        _mapEduList.value = it.data.groupBy { item -> item.userEducationInfo }
                            .map { map ->
                                MapEducation(
                                    edu = map.key,
                                    users = map.value.map { user -> user.toMapSimpleUser() }
                                )
                            }
                    }
                    is RequestResult.Error -> {
                    }
                }
            }, {
            }).addTo(disposable)
    }

    fun searchUser(toString: String) {
    }

    fun selectUsersByCoordinates(position: LatLng) {
        Timber.i(">????")
        _eduUsersCard.value =
            _mapEduList.value.filter { it.edu.location.coordinates.latLng == position }
                .map { it.users }.flatten()
        Timber.i(">???? ${_mapEduList.value}")
    }

    fun openProfile(it: MapSimpleUser) {
        router.openUserProfile(false, it.userInfo.id)
    }
}
