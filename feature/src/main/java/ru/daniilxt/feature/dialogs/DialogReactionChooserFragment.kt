package ru.daniilxt.feature.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.daniilxt.feature.R
import ru.daniilxt.feature.chat.presentation.UserChatFragment
import ru.daniilxt.feature.databinding.DialogEmojiChooserBinding
import ru.daniilxt.feature.dialogs.adapters.EmojiChooserAdapter
import ru.daniilxt.feature.dialogs.viewmodels.EmojiChooserViewModel
import ru.daniilxt.feature.domain.model.Reaction
import ru.daniilxt.feature.domain.model.ReactionWrapper

class DialogReactionChooserFragment : BottomSheetDialogFragment() {

    private var _binding: DialogEmojiChooserBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<EmojiChooserViewModel>()

    private val emojiChooserAdapter: EmojiChooserAdapter by lazy {
        EmojiChooserAdapter {
            handleEmojiClick(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEmojiChooserBinding.inflate(inflater, container, false)
        dialog?.window?.setDimAmount(DIM_ALPHA)
        (dialog as BottomSheetDialog).behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog?.setOnCancelListener {
            // TODO handle when dialog closed
        }

        setFragmentResultListener(UserChatFragment.MESSAGE_ID) { _, bundle ->
            Toast.makeText(
                requireContext(),
                "${bundle.getLong(UserChatFragment.MESSAGE_ID)}",
                Toast.LENGTH_SHORT
            )
                .show()
            viewModel.messageId = bundle.getLong(UserChatFragment.MESSAGE_ID)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dialogEmojiViewChooserRvEmoji.adapter = emojiChooserAdapter
        viewModelSubscriber()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog_Theme
    }

    private fun viewModelSubscriber() {
        lifecycleScope.launch {
            viewModel.emojiListLiveData.collect {
                emojiChooserAdapter.bind(it)
            }
        }
    }

    private fun handleEmojiClick(reaction: Reaction) {
        val bundle = Bundle().apply {
            putParcelable(RECEIVED_DATA_KEY, ReactionWrapper(viewModel.messageId, reaction))
        }
        setFragmentResult(RECEIVED_DATA_KEY, bundle)
        dialog?.dismiss()
    }

    companion object {
        private const val DIM_ALPHA = 0.2F
        private val TAG = DialogReactionChooserFragment::class.java.simpleName
        val RECEIVED_DATA_KEY = "${TAG}_RECEIVED_DATA_KEY"
    }
}
