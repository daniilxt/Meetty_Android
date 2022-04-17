package ru.daniilxt.feature.data.repository

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class FeatureRepositoryImpl @Inject constructor(private val featureDataSource: FeatureDataSource) :
    FeatureRepository {
    override fun getDialogs(userId: Long): Single<RequestResult<List<UserDialog>>> {
        return featureDataSource.getDialogs(userId)
    }
}
