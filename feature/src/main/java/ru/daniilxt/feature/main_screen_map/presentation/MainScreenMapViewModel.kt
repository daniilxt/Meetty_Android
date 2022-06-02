package ru.daniilxt.feature.main_screen_map.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.MapEducation
import ru.daniilxt.feature.domain.model.toMapSimpleUser
import ru.daniilxt.feature.domain.usecase.GetUsersInfoUseCase

class MainScreenMapViewModel(
    private val router: FeatureRouter,
    private val getUsersInfoUseCase: GetUsersInfoUseCase
) : BaseViewModel() {
    private val _mapEduList: MutableStateFlow<List<MapEducation>> =
        MutableStateFlow(emptyList())
    val mapEduList: MutableStateFlow<List<MapEducation>> get() = _mapEduList

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
}
