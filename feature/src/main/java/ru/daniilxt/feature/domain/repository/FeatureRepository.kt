package ru.daniilxt.feature.domain.repository

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.UserDialog

interface FeatureRepository {
    fun getDialogs(userId: Long): Single<RequestResult<List<UserDialog>>>
}