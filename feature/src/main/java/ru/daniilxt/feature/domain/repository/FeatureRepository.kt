package ru.daniilxt.feature.domain.repository

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data_wrapper.EducationInstitute

interface FeatureRepository {
    fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>>
}