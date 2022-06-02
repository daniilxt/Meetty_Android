package ru.daniilxt.feature.domain.usecase

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.ProfileData
import ru.daniilxt.feature.domain.model.Tokens
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class SendRegistrationInfoUseCase @Inject constructor(private val featureRepository: FeatureRepository) {
    operator fun invoke(registrationInfo: ProfileData): Single<RequestResult<Tokens>> {
        return featureRepository.sendRegistrationInfoUseCase(registrationInfo)
    }
}
