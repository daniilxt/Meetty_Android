package ru.daniilxt.feature.data.remote.source

import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.source.FeatureDataSource
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {
}