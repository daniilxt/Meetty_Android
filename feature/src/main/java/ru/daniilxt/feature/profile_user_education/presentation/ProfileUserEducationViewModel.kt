package ru.daniilxt.feature.profile_user_education.presentation

import android.net.Uri
import androidx.core.net.toUri
import kotlinx.coroutines.flow.MutableStateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.AttachType
import ru.daniilxt.feature.domain.model.PhotoAttach
import ru.daniilxt.feature.domain.model.toPhotoAttach

class ProfileUserEducationViewModel(private val router: FeatureRouter) : BaseViewModel() {
    private val _photosList: MutableStateFlow<List<PhotoAttach>> =
        MutableStateFlow(emptyList())
    val photosList: MutableStateFlow<List<PhotoAttach>> get() = _photosList

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

    companion object {
        private val carAddPhotoElement = listOf(PhotoAttach("".toUri(), AttachType.AddPhoto))
    }
}
