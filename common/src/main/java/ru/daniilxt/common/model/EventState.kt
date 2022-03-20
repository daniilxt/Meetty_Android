package studio.clapp.common.model

import ru.daniilxt.common.model.EventError

sealed class EventState {
    object Initial : EventState()
    object Progress : EventState()
    object Success : EventState()
    data class Failure(val error: EventError) : EventState()
}
