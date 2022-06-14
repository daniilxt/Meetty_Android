package ru.daniilxt.feature.data.source

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.EducationInstitute
import ru.daniilxt.feature.domain.model.ChatMessage
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.model.ProfileData
import ru.daniilxt.feature.domain.model.Tokens
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.domain.model.UserProfileInfo

interface FeatureDataSource {
    fun auth(email: String, password: String): Single<RequestResult<Tokens>>
    fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>>
    fun getProfessionalInterests(): Single<RequestResult<List<ProfessionalInterest>>>
    fun sendRegistrationInfoUseCase(registrationInfo: ProfileData): Single<RequestResult<Tokens>>
    fun getUsersCardInfo(): Single<RequestResult<List<UserProfileInfo>>>
    fun getUserProfileInfo(isMy: Boolean, userId: Long): Single<RequestResult<UserProfileInfo>>
    fun getDialogs(): Single<RequestResult<List<UserDialog>>>
    fun getDialogMessages(dialogId: Long): Single<RequestResult<List<ChatMessage>>>
}
