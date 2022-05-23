package ru.daniilxt.feature.profile_steps.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import timber.log.Timber

class ProfileStepsViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    var currentSelectedPage = INITIAL_PAGE
        private set

    fun back() {
        router.back()
    }

    fun setCurrentSelectedPage(position: Int) {
        currentSelectedPage = position
    }

    fun getBundle() {
        Timber.i("Data: ${dataWrapper.getData()}")
    }

    companion object {
        const val INITIAL_PAGE = 0
    }
}
