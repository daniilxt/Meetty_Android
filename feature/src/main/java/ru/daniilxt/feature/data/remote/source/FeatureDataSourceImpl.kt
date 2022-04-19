package ru.daniilxt.feature.data.remote.source

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.model.UserDialog
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {
    override fun getDialogs(userId: Long): Single<RequestResult<List<UserDialog>>> {
        return featureApiService.getEvents(userId).map {
            when {
                it.isSuccessful -> {
                    RequestResult.Success(listOf())
                }
                else -> RequestResult.Success(listOf())
            }
        }
    }
}
