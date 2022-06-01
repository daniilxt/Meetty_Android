package ru.daniilxt.feature.profile_personal_info.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.domain.model.UserPersonalInfo
import ru.daniilxt.feature.files_helper.FilesHelper
import ru.daniilxt.feature.files_helper.FilesHelper.getReadableFileSize
import timber.log.Timber
import java.io.File

class ProfilePersonalInfoViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    var userPhoto: File? = null
    fun isFieldValid(text: String): Boolean = text.isNotBlank()
    fun isPhoneNumberCorrect(simPhoneNumber: String): Boolean {
        return simPhoneNumber.length == PHONE_NUMBER_LENGTH_WITHOUT_COUNTRY_CODE
    }

    fun putRegistrationData(userPersonalInfo: UserPersonalInfo) {
        dataWrapper.setUserPersonalInfo(userPersonalInfo)
    }

    fun setPhoto(context: Context, uri: Uri) {
        FilesHelper.copySelectedFile(context, uri) {
            viewModelScope.launch(Dispatchers.IO) {
                val compressedImageFile = Compressor.compress(context, it)
                Timber.i("AFTER COMPRESS ${getReadableFileSize(compressedImageFile.length())}")
                userPhoto = compressedImageFile
            }
        }
    }

    companion object {
        private const val PHONE_NUMBER_LENGTH_WITHOUT_COUNTRY_CODE = 10
    }
}
