package ru.daniilxt.feature.domain.usecase

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.UserProfileInfo
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class GetUsersInfoUseCase @Inject constructor(private val featureRepository: FeatureRepository) {
    operator fun invoke(): Single<RequestResult<List<UserProfileInfo>>> {
        return featureRepository.getUsersCardInfo()
    }
}
