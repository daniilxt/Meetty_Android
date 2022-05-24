package ru.daniilxt.common.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.daniilxt.common.model.ResponseState
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel>(@LayoutRes fragmentLayoutId: Int) :
    Fragment(fragmentLayoutId) {

    @Inject
    protected open lateinit var viewModel: T

    protected abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        setupViews()
        setupFromArguments()
        setupViewModelSubscriber()
    }

    protected open fun setupViews() {}

    protected open fun setupFromArguments(args: Bundle) {}

    protected open fun setupViewModelSubscriber() {
        viewModel.eventState.observe {
            handleEventState(it)
        }
    }

    protected open fun handleEventState(eventState: ResponseState) {
        if (eventState is ResponseState.Failure) {
            Toast.makeText(requireContext(), eventState.error.errResId, Toast.LENGTH_LONG)
                .show()
        }
    }

    protected abstract fun inject()

    protected inline fun <V> Flow<V>.observe(crossinline collector: suspend (V) -> Unit) {
        lifecycleScope.launch {
            this@observe.collect(collector)
        }
    }

    private fun setupFromArguments() {
        arguments?.let {
            setupFromArguments(it)
        }
    }
}
