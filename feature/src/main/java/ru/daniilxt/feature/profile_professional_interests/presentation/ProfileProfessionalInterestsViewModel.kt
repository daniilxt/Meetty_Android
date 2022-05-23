package ru.daniilxt.feature.profile_professional_interests.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseError
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.usecase.GetProfessionalInterestUseCase

class ProfileProfessionalInterestsViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper,
    private val getProfessionalInterestUseCase: GetProfessionalInterestUseCase
) : BaseViewModel() {
    private val _interests: MutableStateFlow<List<ProfessionalInterest>> =
        MutableStateFlow(emptyList())

    val interests: StateFlow<List<ProfessionalInterest>> get() = _interests

    init {
        getProfessionalInterests()
    }

    private fun getProfessionalInterests() {
        getProfessionalInterestUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        _interests.value = it.data
                    }
                    is RequestResult.Error -> {
                        setEventState(ResponseState.Failure(it.error as ResponseError))
                    }
                }
            }, {
                setEventState(ResponseState.Failure(ResponseError.ConnectionError))
            }
            ).addTo(disposable)
    }

    fun putProfessionalInterestsData(tagList: List<String>) {
        val professionalInterestList = mutableListOf<ProfessionalInterest>()
        tagList.forEach { data ->
            val interest = _interests.value.find { it.interestName == data }
            interest?.let { professionalInterestList.add(it) }
        }
        dataWrapper.setProfessionalInterestsData(professionalInterestList)
    }
}
