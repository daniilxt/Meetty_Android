package ru.daniilxt.feature.chat.presentation

import android.os.Bundle
import android.view.View
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentUserChatBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import timber.log.Timber

class UserChatFragment : BaseFragment<UserChatViewModel>(R.layout.fragment_user_chat) {

    override val binding: FragmentUserChatBinding by viewBinding(FragmentUserChatBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun setupFromArguments(args: Bundle) {
        super.setupFromArguments(args)
        val chatId = args.getLong(CHAT_ID_TAG)
        Timber.tag(TAG).i("chatId = $chatId")
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .userChatComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        private val TAG = UserChatFragment::class.java.simpleName
        private val CHAT_ID_TAG = "${TAG}_CHAT_ID_TAG"
        fun makeBundle(chatId: Long): Bundle {
            return Bundle().apply {
                putLong(CHAT_ID_TAG, chatId)
            }
        }
    }
}
