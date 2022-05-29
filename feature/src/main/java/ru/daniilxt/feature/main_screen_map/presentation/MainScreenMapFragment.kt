package ru.daniilxt.feature.main_screen_map.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.loadIcon
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenMapBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.domain.model.MapEducation

class MainScreenMapFragment :
    BaseFragment<MainScreenMapViewModel>(R.layout.fragment_main_screen_map), OnMapReadyCallback {

    override val binding: FragmentMainScreenMapBinding by viewBinding(FragmentMainScreenMapBinding::bind)
    private lateinit var clusterManager: ClusterManager<EduMapItem>
    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
    }

    override fun setupViews() {
        super.setupViews()
        binding.mapView.getMapAsync(this)
    }

    private fun pinEducationOnMap(eduList: List<MapEducation>) {
        eduList.forEach {
            val edu = it.edu
            map.addMarker(MarkerOptions().position(edu.location.coordinates.latLng)).apply {
                this?.title = edu.name
                this?.loadIcon(requireContext(), edu.logoLink)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.google_map_style)
        )
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                spb,
                MAP_ZOOM_NEAR
            )
        )
        clusterManager = ClusterManager(context, map)
        setUpClusterer()

        viewModel.mapEduList.observe {
            pinEducationOnMap(it)
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun setUpClusterer() {
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
        clusterManager.setOnClusterClickListener {
            Toast.makeText(requireContext(), "Clicked ${it.position}", Toast.LENGTH_SHORT).show()
            true
        }
        addItems()
    }

    // todo delete
    private fun addItems() {

        // Set some lat/lng coordinates to start with.
        var lat = 59.986505
        var lng = 30.348305

        // Add ten cluster items in close proximity, for purposes of this example.
        for (i in 0..9) {
            val offset = i / 60.0
            /*       lat += offset
                   lng += offset*/
            val offsetItem = EduMapItem(LatLng(lat, lng), "Title $i", "Snippet $i")
            clusterManager.addItem(offsetItem)
        }
        lat = 59.976505
        lng = 30.548305
        for (i in 0..20) {
            val offset = i / 60.0
            /*       lat += offset
                   lng += offset*/
            val offsetItem = EduMapItem(LatLng(lat, lng), "Title $i", "Snippet $i")
            clusterManager.addItem(offsetItem)
        }
    }


    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenMapComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        private val spb = LatLng(59.986505, 30.348305)
        private const val MAP_ZOOM_NEAR = 15F
    }
}
