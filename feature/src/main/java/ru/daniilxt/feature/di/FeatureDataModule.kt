package ru.daniilxt.feature.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.daniilxt.common.di.scope.FeatureScope
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.remote.source.FeatureDataSourceImpl
import ru.daniilxt.feature.data.repository.FeatureRepositoryImpl
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.repository.FeatureRepository

@Module
abstract class FeatureDataModule {
    @Binds
    @FeatureScope
    abstract fun bindFeatureRepository(featureRepositoryImpl: FeatureRepositoryImpl): FeatureRepository

    @Binds
    @FeatureScope
    abstract fun bindFeatureDataSource(featureDataSourceImpl: FeatureDataSourceImpl): FeatureDataSource

    companion object {
        @Provides
        @FeatureScope
        fun provideFeatureApiService(retrofit: Retrofit): FeatureApiService =
            retrofit.create(FeatureApiService::class.java)
    }
}