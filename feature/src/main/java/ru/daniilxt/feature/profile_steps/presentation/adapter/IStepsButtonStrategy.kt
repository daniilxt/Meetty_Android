package ru.cartips.feature.profile_steps.presentation.adapter

import android.content.Context
import androidx.fragment.app.Fragment

interface IStepsButtonStrategy {
    fun getEditCarFragmentPosition(): Int
    fun getFragmentList(): List<Fragment>
    fun getFragmentTitleList(context: Context): Array<out String>
}
