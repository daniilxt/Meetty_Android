package ru.daniilxt.feature.profile_personal_info.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.domain.model.UserPersonalInfo

class ProfilePersonalInfoViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    fun isFieldValid(text: String): Boolean = text.isNotBlank()
    fun isPhoneNumberCorrect(simPhoneNumber: String): Boolean {
        return simPhoneNumber.length == PHONE_NUMBER_LENGTH_WITHOUT_COUNTRY_CODE
    }

    fun putRegistrationData(userPersonalInfo: UserPersonalInfo) {
        dataWrapper.setUserPersonalInfo(userPersonalInfo)
    }

    companion object {
        private const val PHONE_NUMBER_LENGTH_WITHOUT_COUNTRY_CODE = 10
    }
}
