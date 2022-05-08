package ru.daniilxt.feature.profile_professional_interests.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.domain.model.ProfessionalInterest

class ProfileProfessionalInterestsViewModel(
    private val router: FeatureRouter,
    private val dataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    private val _interests: MutableStateFlow<List<ProfessionalInterest>> =
        MutableStateFlow(emptyList())

    val interests: StateFlow<List<ProfessionalInterest>> get() = _interests

    init {
        _interests.value = listOf(
            ProfessionalInterest(1, "Программирование"),
            ProfessionalInterest(2, "Бизнес"),
            ProfessionalInterest(3, "Химия"),
            ProfessionalInterest(4, "Физика"),
            ProfessionalInterest(5, "Машиностроение"),
            ProfessionalInterest(6, "Музыка"),
            ProfessionalInterest(7, "Биология"),
            ProfessionalInterest(8, "Аналитика"),
            ProfessionalInterest(9, "История"),
            ProfessionalInterest(10, "Право"),
            ProfessionalInterest(11, "Журналистика"),
            ProfessionalInterest(12, "Общение")
        )
    }

    fun putProfessionalInterestsData(tagList: List<String>) {
        val professionalInterestList = mutableListOf<ProfessionalInterest>()
        tagList.forEach { data ->
            val interest = _interests.value.find { it.interestName == data }
            interest?.let { professionalInterestList.add(it) }
        }
        dataWrapper.setProfessionalInterestsData(professionalInterestList)
    }
}
