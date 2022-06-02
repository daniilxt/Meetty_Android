package ru.daniilxt.feature.calendar_dialog.dialogs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.common.extensions.getStandaloneName
import ru.daniilxt.feature.databinding.SpinnerDatePickerMonthItemBinding
import java.time.Month

@SuppressLint("NewApi")
class SpinnerDatePickerMonthAdapter(val context: Context) :
    RecyclerView.Adapter<SpinnerDatePickerMonthViewHolder>() {
    var monthList: MutableList<Month> = mutableListOf()

    init {
        monthList.addAll(Month.values())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpinnerDatePickerMonthViewHolder {
        val binding = SpinnerDatePickerMonthItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpinnerDatePickerMonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpinnerDatePickerMonthViewHolder, position: Int) {
        with(holder.binding.spinnerDatePickerMonthTvItem) {
            text = monthList[position].getStandaloneName()
        }
    }

    override fun getItemCount() = monthList.size
}

class SpinnerDatePickerMonthViewHolder(val binding: SpinnerDatePickerMonthItemBinding) :
    RecyclerView.ViewHolder(binding.root)
