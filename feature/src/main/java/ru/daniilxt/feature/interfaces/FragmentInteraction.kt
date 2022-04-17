package ru.daniilxt.feature.interfaces

import ru.daniilxt.feature.domain.model.Reaction

interface FragmentInteraction {
    fun onSendResult(reaction: Reaction)
}
