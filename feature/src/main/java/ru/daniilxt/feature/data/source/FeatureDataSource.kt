package ru.daniilxt.feature.data.source

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.UserDialog

interface FeatureDataSource {
    fun getDialogs(userId: Long): Single<RequestResult<List<UserDialog>>>
}
