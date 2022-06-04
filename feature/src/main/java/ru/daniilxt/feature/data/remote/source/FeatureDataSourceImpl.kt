package ru.daniilxt.feature.data.remote.source

import io.reactivex.Single
import retrofit2.Response
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseError
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.remote.model.body.LoginCredentialsBody
import ru.daniilxt.feature.data.remote.model.error.LoginError
import ru.daniilxt.feature.data.remote.model.error.RegistrationError
import ru.daniilxt.feature.data.remote.model.response.toEducationInstitute
import ru.daniilxt.feature.data.remote.model.response.toProfessionalInterest
import ru.daniilxt.feature.data.remote.model.response.toTokens
import ru.daniilxt.feature.data.remote.model.response.toUserDialog
import ru.daniilxt.feature.data.remote.model.response.toUserProfileInfo
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.model.EducationInstitute
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.model.ProfileData
import ru.daniilxt.feature.domain.model.Tokens
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.domain.model.UserProfileInfo
import ru.daniilxt.feature.domain.model.toRegistrationInfoBody
import java.net.HttpURLConnection
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {

    override fun auth(email: String, password: String): Single<RequestResult<Tokens>> {
        return featureApiService.auth(LoginCredentialsBody(email, password))
            .map {
                when {
                    it.isSuccessful -> {
                        val res = it.body()
                        val access = res?.accessToken
                        val refresh = res?.refreshToken
                        if (res == null) {
                            //  Timber.tag(TAG).e("One of tokens is null, response=$it")
                            RequestResult.Error(LoginError.Unknown)
                        } else {
                            RequestResult.Success(res.toTokens())
                        }
                    }
                    it.code() == HttpURLConnection.HTTP_FORBIDDEN -> {
                        RequestResult.Error(LoginError.InvalidCredentials)
                    }
                    else -> {
                        RequestResult.Error(LoginError.Unknown)
                    }
                }
            }
    }

    override fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>> {
        return featureApiService.getEducationInstitutes().map { response ->
            getSingleCollectionData(response) {
                response.body()!!.map { item -> item.toEducationInstitute() }
            }
        }
    }

    override fun getProfessionalInterests(): Single<RequestResult<List<ProfessionalInterest>>> {
        return featureApiService.getProfessionalInterests().map { response ->
            getSingleCollectionData(response) {
                response.body()!!.map { it.toProfessionalInterest() }
            }
        }
    }

    override fun sendRegistrationInfoUseCase(registrationInfo: ProfileData):
        Single<RequestResult<Tokens>> {
        return featureApiService.sendRegistrationInfo(registrationInfo.toRegistrationInfoBody())
            .map { response ->
                when {
                    response.isSuccessful -> {
                        val res = response.body()
                        if (res != null) {
                            RequestResult.Success(res.toTokens())
                        } else {
                            RequestResult.Error(ResponseError.UnknownError)
                        }
                    }
                    response.code() == HttpURLConnection.HTTP_CONFLICT -> {
                        RequestResult.Error(RegistrationError.UserAlreadyExists)
                    }
                    else -> {
                        RequestResult.Error(ResponseError.UnknownError)
                    }
                }
            }
    }

    override fun getUsersCardInfo(): Single<RequestResult<List<UserProfileInfo>>> {
        return featureApiService.getUsersProfileInfo().map { response ->
            getSingleCollectionData(response) {
                response.body()!!.map { it.toUserProfileInfo() }
            }
        }
    }

    override fun getUserProfileInfo(
        isMy: Boolean,
        userId: Long
    ): Single<RequestResult<UserProfileInfo>> {
        val single = if (isMy) {
            featureApiService.getUserProfileInfo()
        } else {
            featureApiService.getUserProfileInfoById(userId)
        }
        return single.map { response ->
            getSingleData(response) {
                response.body()!!.toUserProfileInfo()
            }
        }
    }

    override fun getDialogs(): Single<RequestResult<List<UserDialog>>> {
        return featureApiService.getUserDialogs().map { response ->
            getSingleCollectionData(response) {
                response.body()!!.map { it.toUserDialog() }
            }
        }
    }

    private fun <T, U> getSingleData(
        response: Response<T>,
        onSuccess: () -> U,
    ) = when {
        response.isSuccessful -> {
            val res = response.body()
            if (res != null) {
                RequestResult.Success(onSuccess())
            } else {
                RequestResult.Error(ResponseError.UnknownError)
            }
        }
        else -> {
            RequestResult.Error(ResponseError.UnknownError)
        }
    }

    private fun <T, U> getSingleCollectionData(
        response: Response<List<T>>,
        onSuccess: () -> List<U>,
    ) = when {
        response.isSuccessful -> {
            val res = response.body()
            if (res != null) {
                RequestResult.Success(onSuccess())
            } else {
                RequestResult.Error(ResponseError.UnknownError)
            }
        }
        else -> {
            RequestResult.Error(ResponseError.UnknownError)
        }
    }
}
