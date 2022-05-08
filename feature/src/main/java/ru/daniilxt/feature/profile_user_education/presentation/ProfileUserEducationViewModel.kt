package ru.daniilxt.feature.profile_user_education.presentation

import android.content.Context
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
import ru.daniilxt.feature.files_helper.FilesHelper
import timber.log.Timber
import java.io.File

class ProfileUserEducationViewModel(
    private val router: FeatureRouter,
    private val profileDataWrapper: ProfileDataWrapper
) : BaseViewModel() {
    private val _photosList: MutableStateFlow<Set<PhotoAttach>> =
        MutableStateFlow(emptySet())
    val photosList: MutableStateFlow<Set<PhotoAttach>> get() = _photosList

    // TODO CITIES WILL RETURNED FROM THE SERVER
    val spb = City(1, "Санкт-Петербург")
    val msk = City(2, "Москва")
    val nsk = City(3, "Новосибирск")

    private val _citiesList: MutableStateFlow<List<City>> = MutableStateFlow(
        listOf(spb, msk, nsk)
    )
    val citiesList: StateFlow<List<City>> get() = _citiesList

    private val _parentInstitutesList: MutableStateFlow<List<EducationInstitute>> =
        MutableStateFlow(
            listOf(
                EducationInstitute(1, "Политех", spb),
                EducationInstitute(4, "ИТМО", spb),
                EducationInstitute(2, "НГУ", nsk),
                EducationInstitute(3, "МФТИ", msk)
            )
        )
    private val _institutesList: MutableStateFlow<List<EducationInstitute>> = MutableStateFlow(
        _parentInstitutesList.value
    )

    val institutesList: StateFlow<List<EducationInstitute>> get() = _institutesList

    init {
        _photosList.value = addPhotoElement
    }

    fun setPhotos(list: List<Uri>) {
        val prevList = _photosList.value.toList().subList(0, _photosList.value.size - 1)
        _photosList.value =
            (prevList + list.map { it.toPhotoAttach() } + addPhotoElement).toSet()
    }

    fun deletePhoto(photoAttach: PhotoAttach) {
        _photosList.value = _photosList.value.filter { it != photoAttach }.toSet()
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
            Timber.i("???? $userEducationInfo")
        }
    }

    private fun getInstitute(institute: String): EducationInstitute? {
        return _institutesList.value.find { it.instituteName == institute }
    }

    private fun getCityByName(city: String): City? {
        return _citiesList.value.find { it.cityName == city }
    }

    fun filterEduByCity(city: String) {
        val userCity = getCityByName(city)
        _institutesList.value = _parentInstitutesList.value.filter { it.city == userCity }
    }

    private fun copySelectedFiles(context: Context): List<File> {
        val files = mutableListOf<File>()
        val uriRes =
            _photosList.value.toList().subList(0, _photosList.value.size - 1).map { it.photoUri }
        uriRes.forEach { uri ->
            FilesHelper.copySelectedFile(context, uri) {
                files.add(it)
            }
        }
        return files
    }

    companion object {
        private val addPhotoElement = setOf(PhotoAttach("".toUri(), AttachType.AddPhoto))
    }
}
