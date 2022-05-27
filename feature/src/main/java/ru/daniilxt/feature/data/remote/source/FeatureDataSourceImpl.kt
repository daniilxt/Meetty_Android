package ru.daniilxt.feature.data.remote.source

import io.reactivex.Single
import retrofit2.Response
import ru.daniilxt.common.error.RegistrationError
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseError
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.remote.model.response.toEducationInstitute
import ru.daniilxt.feature.data.remote.model.response.toProfessionalInterest
import ru.daniilxt.feature.data.remote.model.response.toTokens
import ru.daniilxt.feature.data.remote.model.response.toUserCardInfo
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.model.EducationInstitute
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.model.ProfileData
import ru.daniilxt.feature.domain.model.Tokens
import ru.daniilxt.feature.domain.model.UserCardInfo
import ru.daniilxt.feature.domain.model.toRegistrationInfoBody
import java.net.HttpURLConnection
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {
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

    override fun getUsersCardInfo(): Single<RequestResult<List<UserCardInfo>>> {
        return featureApiService.getUsersProfileInfo().map { response ->
            getSingleCollectionData(response) {
                response.body()!!.map { it.toUserCardInfo() }
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
