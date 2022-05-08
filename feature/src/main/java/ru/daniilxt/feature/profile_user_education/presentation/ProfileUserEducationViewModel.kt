package ru.daniilxt.feature.profile_user_education.presentation

import android.net.Uri
import androidx.core.net.toUri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.data_wrapper.City
import ru.daniilxt.feature.data_wrapper.EducationInstitute
import ru.daniilxt.feature.data_wrapper.ProfileDataWrapper
import ru.daniilxt.feature.data_wrapper.UserEducationInfo
import ru.daniilxt.feature.domain.model.AttachType
import ru.daniilxt.feature.domain.model.PhotoAttach
import ru.daniilxt.feature.domain.model.toPhotoAttach
import timber.log.Timber

class ProfileUserEducationViewModel(
    private val router: FeatureRouter,
    private val profileDataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    private val _photosList: MutableStateFlow<List<PhotoAttach>> =
        MutableStateFlow(emptyList())
    val photosList: MutableStateFlow<List<PhotoAttach>> get() = _photosList

    // TODO CITIES WILL RETURNED FROM THE SERVER
    val spb = City(1, "Санкт-Петербург")
    val msk = City(2, "Москва")
    val nsk = City(3, "Новосибирск")

    private val _citiesList: MutableStateFlow<List<City>> = MutableStateFlow(
        listOf(spb, msk, nsk)
    )
    val citiesList: StateFlow<List<City>> get() = _citiesList

    private val _institutesList: MutableStateFlow<List<EducationInstitute>> = MutableStateFlow(
        listOf(
            EducationInstitute(1, "Политех", spb),
            EducationInstitute(2, "НГУ", nsk),
            EducationInstitute(3, "МФТИ", msk)
        )
    )

    val institutesList: StateFlow<List<EducationInstitute>> get() = _institutesList

    init {
        _photosList.value = carAddPhotoElement
    }

    fun setPhotos(list: List<Uri>) {
        val prevFaultList = _photosList.value.subList(0, _photosList.value.lastIndex)
        _photosList.value =
            prevFaultList + list.map { it.toPhotoAttach() } + carAddPhotoElement
    }

    fun deletePhoto(carFault: PhotoAttach) {
        _photosList.value = _photosList.value.filter { it != carFault }
    }

    fun putEducationData(city: String, institute: String) {
        val userCity = getCityByName(city)
        val userInstitute = getInstitute(institute)
        if (userCity != null && userInstitute != null) {
            val userEducationInfo = UserEducationInfo(
                city = userCity,
                institute = userInstitute,
                photos = emptyList()
            )
            profileDataWrapper.setUserEducationInfo(userEducationInfo)
            Timber.i("???? ${userEducationInfo}")
        }
    }

    private fun getInstitute(institute: String): EducationInstitute? {
        return _institutesList.value.find { it.instituteName == institute }
    }

    private fun getCityByName(city: String): City? {
        return _citiesList.value.find { it.cityName == city }
    }

    companion object {
        private val carAddPhotoElement = listOf(PhotoAttach("".toUri(), AttachType.AddPhoto))
    }
}
