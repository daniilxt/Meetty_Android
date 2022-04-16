package ru.daniilxt.feature.chat.presentation

fun dtoToEntity(dto: ChatSocketMessage) : Message2 {
    return Message2(
        dto.datetime,
        dto.text,
        dto.author,
        dto.receiver
    )
}

fun entityToDto(entity: Message2) : ChatSocketMessage {
    return ChatSocketMessage(
        entity.text,
        entity.author,
        entity.datetime,
        entity.receiver
    )
}