package ru.daniilxt.feature.main_screen_map.presentation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout
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
import ru.daniilxt.feature.main_screen_map.presentation.adapter.EduUserCardAdapter

class MainScreenMapFragment :
    BaseFragment<MainScreenMapViewModel>(R.layout.fragment_main_screen_map), OnMapReadyCallback {

    override val binding: FragmentMainScreenMapBinding by viewBinding(FragmentMainScreenMapBinding::bind)
    private lateinit var clusterManager: ClusterManager<EduMapItem>
    private lateinit var map: GoogleMap

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val eduUserCardAdapter = EduUserCardAdapter(onItemClickListener = { data, sharedView ->
        viewModel.openProfile(data)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
    }

    override fun setupViews() {
        super.setupViews()
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetUsers.root)
        binding.bottomSheetUsers.rvEduUsers.adapter = eduUserCardAdapter
        binding.mapView.getMapAsync(this)

        initBottomSheetInputForm()
        setBottomSheetBehaviorListener()
        setSearchListener()
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.google_map_style)
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(spb, MAP_ZOOM_NEAR))
        map.setOnMarkerClickListener { marker ->
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, MAP_ZOOM_NEAR))
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                viewModel.selectUsersByCoordinates(marker.position)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                changeSearchVisibility(false)
                marker.showInfoWindow()
            }
            true
        }
        viewModel.mapEduList.observe {
            pinEducationOnMap(it)
        }
        viewModel.eduUsersCard.observe {
            eduUserCardAdapter.bind(it)
        }
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

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenMapComponentFactory()
            .create(this)
            .inject(this)
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun setUpClusterer() {
    }

    private fun initBottomSheetInputForm() {
        with(binding.bottomSheetUsers.etSearch) {
            etLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
            etLayout.setEndIconTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.background_third_dark
                    )
                )
            )
            etLayout.setEndIconDrawable(R.drawable.ic_search_24)
            etLayout.setBackgroundResource(android.R.color.transparent)
            inputFormEt.setBackgroundResource(android.R.color.transparent)
            inputFormEt.hint = getString(R.string.search)
            inputFormEt.setHintTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.background_third_dark
                )
            )
        }
    }

    private fun setBottomSheetBehaviorListener() {
        bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        changeSearchVisibility(true)
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        changeSearchVisibility(true)
                    }
                }
            })
    }

    private fun changeSearchVisibility(isVisible: Boolean) {
        with(binding.bottomSheetUsers) {
            etSearch.textWrapper.isVisible = isVisible
        }
    }

    private fun setSearchListener() {
        val searchEt =
            binding.bottomSheetUsers.etSearch.inputFormEt
        searchEt.addTextChangedListener(
            beforeTextChanged = { text, _, _, _ -> viewModel.searchUser(text.toString()) }
        )
    }

    companion object {
        private val spb = LatLng(59.986505, 30.348305)
        private const val MAP_ZOOM_NEAR = 15F
    }
}
