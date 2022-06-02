package ru.daniilxt.feature.profile_professional_interests.presentation

import android.view.View
import android.widget.Toast
import androidx.core.view.children
import com.google.android.material.chip.Chip
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileProfessionalInterestsBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields

class ProfileProfessionalInterestsFragment :
    BaseFragment<ProfileProfessionalInterestsViewModel>(R.layout.fragment_profile_professional_interests),
    IValidateFragmentFields {

    override val binding: FragmentProfileProfessionalInterestsBinding by viewBinding(
        FragmentProfileProfessionalInterestsBinding::bind
    )

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileProfessionalInterestsComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.interests.observe { interests ->
            createChips(interests.map { it.interestName })
        }
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        val isOk = isFieldsCorrectAndPutToBundle()
        callback(isOk)
    }

    private fun createChips(list: List<String>) {
        binding.chipGroup.removeAllViews()
        for (item in list) {
            val chip = (layoutInflater.inflate(R.layout.item_chip, null) as Chip).apply {
                id = View.generateViewId()
                text = item
            }
            binding.chipGroup.addView(chip)
        }
    }

    private fun isFieldsCorrectAndPutToBundle(): Boolean {
        if (binding.chipGroup.checkedChipIds.isEmpty()) {
            Toast.makeText(
                requireContext(),
                R.string.choose_at_least_one_category,
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        val tagList = binding.chipGroup.children
            .toList()
            .filter { (it as Chip).isChecked }
            .map { (it as Chip).text.toString() }
            .toList()
        viewModel.putProfessionalInterestsData(tagList)
        return true
    }

    companion object {
        fun newInstance(): ProfileProfessionalInterestsFragment {
            return ProfileProfessionalInterestsFragment()
        }
    }
}
