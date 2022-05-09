package ru.daniilxt.feature.domain.model

sealed class AttachType {
    object PhotoUri : AttachType()
    object AddPhoto : AttachType()
}