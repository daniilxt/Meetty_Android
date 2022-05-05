package ru.daniilxt.feature.profile_personal_info.presentation

import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper

class ProfilePersonalInfoViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel()
