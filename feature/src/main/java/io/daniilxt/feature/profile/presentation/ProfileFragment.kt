package io.daniilxt.feature.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.setDebounceClickListener
import io.daniilxt.common.extensions.setLightStatusBar
import io.daniilxt.common.extensions.setStatusBarColor
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentProfileBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModel: ProfileViewModel

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.frgProfileToolbar.includeToolbarBackTvTitle.text = getString(R.string.profile)
        binding.frgProfileToolbar.includeToolbarBackIbBack.setDebounceClickListener {
            viewModel.onBackPressed()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileComponentFactory()
            .create(this)
            .inject(this)
    }
}