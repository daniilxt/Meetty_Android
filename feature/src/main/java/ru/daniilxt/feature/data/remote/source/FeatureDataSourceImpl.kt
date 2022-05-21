package ru.daniilxt.feature.data.remote.source

import io.reactivex.Single
import retrofit2.Response
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseError
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.remote.model.toEducationInstitute
import ru.daniilxt.feature.data.remote.model.toProfessionalInterest
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.data_wrapper.EducationInstitute
import ru.daniilxt.feature.domain.model.ProfessionalInterest
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