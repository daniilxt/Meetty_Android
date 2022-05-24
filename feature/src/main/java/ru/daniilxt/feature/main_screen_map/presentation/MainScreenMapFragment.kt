package ru.daniilxt.feature.main_screen_map.presentation

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenMapBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class MainScreenMapFragment :
    BaseFragment<MainScreenMapViewModel>(R.layout.fragment_main_screen_map), OnMapReadyCallback {

    override val binding: FragmentMainScreenMapBinding by viewBinding(FragmentMainScreenMapBinding::bind)
    private lateinit var mMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
    }

    override fun setupViews() {
        super.setupViews()
        binding.mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                spb,
                MAP_ZOOM_NEAR
            )
        )
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
