package ru.daniilxt.feature.data.remote.source

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseError
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.remote.model.toEducationInstitute
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.data_wrapper.EducationInstitute
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {
    override fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>> {
        return featureApiService.getEducationInstitutes().map {
            when {
                it.isSuccessful -> {
                    val res = it.body()
                    if (res != null) {
                        RequestResult.Success(res.map { item -> item.toEducationInstitute() })
                    } else {
                        RequestResult.Error(ResponseError.UnknownError)
                    }
                }
                else -> {
                    RequestResult.Error(ResponseError.UnknownError)
                }
            }
        }
    }
}