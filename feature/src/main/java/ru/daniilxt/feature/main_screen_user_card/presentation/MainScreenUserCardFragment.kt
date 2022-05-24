package ru.daniilxt.feature.main_screen_user_card.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenUserCardBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class MainScreenUserCardFragment :
    BaseFragment<MainScreenUserCardViewModel>(R.layout.fragment_main_screen_user_card) {

    override val binding: FragmentMainScreenUserCardBinding by viewBinding(
        FragmentMainScreenUserCardBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenUserCardComponentFactory()
            .create(this)
            .inject(this)
    }
}
