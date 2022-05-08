package ru.daniilxt.feature.profile_user_education.presentation

import android.content.Intent
import android.content.pm.ActivityInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.ui.MatisseActivity
import ru.daniilxt.common.base.BaseDelegate
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.common.utils.CoilImageEngine
import ru.daniilxt.common.utils.PermissionUtils
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentProfileUserEducationBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.profile_steps.presentation.adapter.IValidateFragmentFields
import ru.daniilxt.feature.profile_user_education.presentation.adapter.PhotoStudentBookAdapter
import java.util.* // ktlint-disable no-wildcard-imports

class ProfileUserEducationFragment :
    BaseFragment<ProfileUserEducationViewModel>(R.layout.fragment_profile_user_education),
    IValidateFragmentFields {

    override val binding: FragmentProfileUserEducationBinding by viewBinding(
        FragmentProfileUserEducationBinding::bind
    )
    private val inputFieldDelegate by lazy {
        InputFieldDelegate(binding, viewModel)
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val photoStudentBookAdapter: PhotoStudentBookAdapter by lazy {
        PhotoStudentBookAdapter(onDeletePhotoClickListener = {
            viewModel.deletePhoto(it)
        }, onAddPhotoClickListener = {
            requestAndSelectPhoto()
        })
    }

    override fun setupViews() {
        super.setupViews()
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode != 0) {
                    val res = Matisse.obtainResult(result.data)
                    viewModel.setPhotos(res)
                }
            }
        addNewDelegate(inputFieldDelegate)
        initAdapters()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.photosList.observe {
            photoStudentBookAdapter.bind(it)
        }
        viewModel.citiesList.observe { cityList ->
            setSpinnerListAdapter(binding.spinnerCities.spinner, cityList.map { it.cityName })
        }
        viewModel.institutesList.observe { instituteList ->
            setSpinnerListAdapter(
                binding.spinnerInstitutes.spinner,
                instituteList.map { it.instituteName }
            )
        }
    }

    private fun setSpinnerListAdapter(spinner: AutoCompleteTextView, data: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_text_item, data)
        spinner.setAdapter(adapter)
    }

    override fun isFieldsFilled(callback: (isFilled: Boolean) -> Unit) {
        val isFieldsFilled = inputFieldDelegate.isFieldsCorrectAndPutToBundle()
        callback(isFieldsFilled)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .profileUserEducationComponentFactory()
            .create(this)
            .inject(this)
    }

    private fun initAdapters() {
        binding.rvPhotos.adapter = photoStudentBookAdapter
    }

    private fun addNewDelegate(etDelegate: BaseDelegate) {
        etDelegate.loadDelegate()
    }

    private fun requestAndSelectPhoto() {
        PermissionUtils.checkStoragePermissions(this) {
            Matisse.from(this)
                .choose(
                    EnumSet.of(
                        MimeType.JPEG,
                        MimeType.PNG,
                        MimeType.BMP,
                        MimeType.WEBP
                    )
                )
                .countable(true)
                .maxSelectable(Int.MAX_VALUE)
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .showSingleMediaType(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(1f)
                .imageEngine(CoilImageEngine())
                .theme(R.style.Theme_Matisse)

            val intent = Intent(requireContext(), MatisseActivity::class.java)

            resultLauncher.launch(intent)
        }
    }

    companion object {
        fun newInstance(): ProfileUserEducationFragment {
            return ProfileUserEducationFragment()
        }
    }
}
