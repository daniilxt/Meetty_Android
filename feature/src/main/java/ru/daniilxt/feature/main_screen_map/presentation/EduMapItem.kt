package ru.daniilxt.feature.main_screen_map.presentation

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class EduMapItem(
    private val position: LatLng,
    private val title: String,
    private val snippet: String
) : ClusterItem {
    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String {
        return snippet
    }
}