package ru.daniilxt.feature.data.source

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data_wrapper.EducationInstitute

interface FeatureDataSource {
    fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>>
}