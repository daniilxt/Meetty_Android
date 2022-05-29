package ru.daniilxt.feature.domain.model

import com.google.android.gms.maps.model.LatLng

data class Coordinates(
    val latitude: Float,
    val longitude: Float
) {
    val latLng: LatLng = LatLng(latitude.toDouble(), longitude.toDouble())
}
