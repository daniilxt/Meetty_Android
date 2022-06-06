package ru.daniilxt.feature.chat.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.margin
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.statusBarHeightInPx
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.common.token.TokenRepository
import ru.daniilxt.feature.R
import ru.daniilxt.feature.chat.presentation.adapter.MessageAdapter
import ru.daniilxt.feature.databinding.FragmentUserChatBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.dialogs.DialogReactionChooserFragment
import ru.daniilxt.feature.domain.model.ReactionWrapper
import ru.daniilxt.feature.domain.model.UserDialog
import timber.log.Timber
import javax.inject.Inject

class UserChatFragment :
    BaseFragment<UserChatViewModel>(R.layout.fragment_user_chat) {

    override val binding: FragmentUserChatBinding by viewBinding(FragmentUserChatBinding::bind)

    @Inject
    lateinit var tokenRepository: TokenRepository

    private val emojiChooserBottomDialog: DialogReactionChooserFragment by lazy {
        DialogReactionChooserFragment()
    }
    private val messageAdapter =
        MessageAdapter(onEmojiClickListener = { messageId, reaction ->
        }, onMessageLongCLickListener = { id ->
            val bundle = Bundle().apply {
                putLong(MESSAGE_ID, id)
            }
            setFragmentResult(MESSAGE_ID, bundle)
            emojiChooserBottomDialog.show(
                parentFragmentManager, TAG_EMOJI_CHOOSER_BOTTOM_SHEET
            )
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()

        binding.layoutToolbar.root.margin(top = requireActivity().statusBarHeightInPx)

        setFragmentResultListener(DialogReactionChooserFragment.RECEIVED_DATA_KEY) { _, bundle ->
            val reactionWrapper =
                bundle.getParcelable<ReactionWrapper>(DialogReactionChooserFragment.RECEIVED_DATA_KEY)
            reactionWrapper?.let { viewModel.setReaction(it) }
        }
    }

    override fun setupFromArguments(args: Bundle) {
        super.setupFromArguments(args)
        val chat = args.getParcelable<UserDialog>(CHAT_ID_TAG)
        chat?.let { viewModel.userDialog(it) }
        Timber.tag(TAG).i("chatId = ${chat?.id}")
    }

    override fun setupViews() {
        super.setupViews()
        initToolbar()
        initRecycler()
        binding.etMessage.ibSend.setDebounceClickListener {
            if (binding.etMessage.inputFormEt.text?.isNotEmpty() == true) {
                viewModel.sendMessage(binding.etMessage.inputFormEt.text.toString())
                binding.etMessage.inputFormEt.text?.clear()
            }
        }
    }

    private fun initRecycler() {
        binding.messagesRv.adapter = messageAdapter
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userMessages.observe {
            messageAdapter.bind(it)
            binding.messagesRv.scrollToPosition((binding.messagesRv.adapter?.itemCount ?: 0) - 1)
        }
    }

    private fun initToolbar() {
        lifecycleScope.launch {
            delay(200)
            viewModel.myId = tokenRepository.getCurrentUserId()
            binding.layoutToolbar.includeToolbarBackTvTitle.text =
                viewModel.userDialog.returnCompanionUser(tokenRepository.getCurrentUserId())
                    .getCapitalizedFullUserName()
        }
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
        private val TAG_EMOJI_CHOOSER_BOTTOM_SHEET = "${TAG}_EMOJI_CHOOSER_BOTTOM_SHEET"
        val MESSAGE_ID = "${TAG}_MESSAGE_ID"
        fun makeBundle(userDialog: UserDialog): Bundle {
            return Bundle().apply {
                putParcelable(CHAT_ID_TAG, userDialog)
            }
        }
    }
}
