package ru.daniilxt.feature.data.repository

import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class FeatureRepositoryImpl @Inject constructor(private val featureDataSource: FeatureDataSource) :
    FeatureRepository {
}