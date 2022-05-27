package ru.daniilxt.feature.data.source

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.EducationInstitute
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.model.ProfileData
import ru.daniilxt.feature.domain.model.Tokens
import ru.daniilxt.feature.domain.model.UserCardInfo

interface FeatureDataSource {
    fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>>
    fun getProfessionalInterests(): Single<RequestResult<List<ProfessionalInterest>>>
    fun sendRegistrationInfoUseCase(registrationInfo: ProfileData): Single<RequestResult<Tokens>>
    fun getUsersCardInfo(): Single<RequestResult<List<UserCardInfo>>>
}
