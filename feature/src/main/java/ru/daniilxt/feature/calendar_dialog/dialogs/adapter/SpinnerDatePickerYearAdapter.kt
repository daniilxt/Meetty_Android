package ru.daniilxt.feature.calendar_dialog.dialogs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.daniilxt.feature.databinding.SpinnerDatePickerYearItemBinding

class SpinnerDatePickerYearAdapter :
    RecyclerView.Adapter<SpinnerDatePickerYearViewHolder>() {
    var yearList: MutableList<Int> = mutableListOf()

    init {
        for (i in 1940..2022) {
            yearList.add(i)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpinnerDatePickerYearViewHolder {
        val binding =
            SpinnerDatePickerYearItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SpinnerDatePickerYearViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpinnerDatePickerYearViewHolder, position: Int) {
        with(holder.binding.spinnerDatePickerYearTvItem) {
            text = yearList[position].toString()
        }
    }

    override fun getItemCount() = yearList.size
}

class SpinnerDatePickerYearViewHolder(val binding: SpinnerDatePickerYearItemBinding) :
    RecyclerView.ViewHolder(binding.root)
