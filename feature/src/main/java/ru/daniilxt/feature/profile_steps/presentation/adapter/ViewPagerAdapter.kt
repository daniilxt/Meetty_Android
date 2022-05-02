package ru.daniilxt.feature.profile_steps.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, private val fragments: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun checkFragmentAlacrity(fragment: Fragment, callback: (isTrue: Boolean) -> Unit) {
        val currentFragment = fragment as? IValidateFragmentFields
        currentFragment?.isFieldsFilled {
            callback(it)
        }
    }
}
