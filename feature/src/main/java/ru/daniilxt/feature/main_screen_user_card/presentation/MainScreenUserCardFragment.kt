package ru.daniilxt.feature.main_screen_user_card.presentation

import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeableMethod
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenUserCardBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.main_screen_user_card.presentation.adapter.SwipedUserCardAdapter

class MainScreenUserCardFragment :
    BaseFragment<MainScreenUserCardViewModel>(R.layout.fragment_main_screen_user_card),
    CardStackListener {

    override val binding: FragmentMainScreenUserCardBinding by viewBinding(
        FragmentMainScreenUserCardBinding::bind
    )

    private val userCardsAdapter by lazy {
        SwipedUserCardAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = CardStackLayoutManager(requireContext(), this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        binding.stackView.layoutManager = layoutManager
        binding.stackView.adapter = userCardsAdapter
        binding.stackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.userCards.observe {
            userCardsAdapter.bind(it)
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenUserCardComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction) {
        if (direction == Direction.Left) {
            Toast.makeText(requireContext(), "Вы отменили", Toast.LENGTH_SHORT).show()
        } else if (direction == Direction.Right) {
            Toast.makeText(requireContext(), "Написать?", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}
