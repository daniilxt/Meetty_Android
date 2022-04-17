package ru.daniilxt.feature.chat.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.chat.presentation.adapter.MessageAdapter
import ru.daniilxt.feature.databinding.FragmentUserChatBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.dialogs.DialogReactionChooserFragment
import ru.daniilxt.feature.domain.model.ReactionWrapper
import ru.daniilxt.feature.domain.model.UserDialog
import ru.daniilxt.feature.user_dialogs.presentation.util.UserDialogsProvider
import timber.log.Timber

class UserChatFragment :
    BaseFragment<UserChatViewModel>(R.layout.fragment_user_chat) {

    override val binding: FragmentUserChatBinding by viewBinding(FragmentUserChatBinding::bind)
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
        setFragmentResultListener(DialogReactionChooserFragment.RECEIVED_DATA_KEY) { _, bundle ->
            Toast.makeText(requireContext(), "GOOD", Toast.LENGTH_SHORT).show()

             val reactionWrapper = bundle.getParcelable<ReactionWrapper>(DialogReactionChooserFragment.RECEIVED_DATA_KEY)
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
                viewModel.sendMessage2(binding.etMessage.inputFormEt.text.toString())
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
        binding.layoutToolbar.includeToolbarBackTvTitle.text =
            viewModel.userDialog.returnCompanionUser(UserDialogsProvider.myUser)
                .getCapitalizedFullUserName()
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
