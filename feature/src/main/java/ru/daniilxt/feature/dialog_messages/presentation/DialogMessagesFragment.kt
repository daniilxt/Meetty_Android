package ru.daniilxt.feature.dialog_messages.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentDialogMessagesBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class DialogMessagesFragment :
    BaseFragment<DialogMessagesViewModel>(R.layout.fragment_dialog_messages) {

    override val binding: FragmentDialogMessagesBinding by viewBinding(FragmentDialogMessagesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .dialogMessagesComponentFactory()
            .create(this)
            .inject(this)
    }
}
