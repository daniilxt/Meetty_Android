package ru.daniilxt.feature.domain.usecase

import io.reactivex.Single
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class GetUserDialogsUseCase @Inject constructor(private val featureRepository: FeatureRepository) {
    operator fun invoke(): Single<RequestResult<List<UserDialog>>> {
        return featureRepository.getDialogs()
    }
}
