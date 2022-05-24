package ru.daniilxt.feature.domain.usecase

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.Tokens
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val featureRepository: FeatureRepository) {
    operator fun invoke(email: String, password: String): Single<RequestResult<Tokens>> {
        return featureRepository.auth(email, password)
    }
}
