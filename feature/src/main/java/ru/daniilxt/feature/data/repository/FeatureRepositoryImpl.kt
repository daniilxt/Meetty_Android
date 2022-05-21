package ru.daniilxt.feature.data.repository

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.data_wrapper.EducationInstitute
import ru.daniilxt.feature.domain.model.ProfessionalInterest
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class FeatureRepositoryImpl @Inject constructor(private val featureDataSource: FeatureDataSource) :
    FeatureRepository {
    override fun getEducationInstitutes(): Single<RequestResult<List<EducationInstitute>>> {
        return featureDataSource.getEducationInstitutes()
    }

    override fun getProfessionalInterests(): Single<RequestResult<List<ProfessionalInterest>>> {
        return featureDataSource.getProfessionalInterests()
    }
}