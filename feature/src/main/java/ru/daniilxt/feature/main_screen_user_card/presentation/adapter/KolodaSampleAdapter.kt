package ru.daniilxt.feature.main_screen_user_card.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemUserFullCardBinding

data class SwypedUserCard(
    val name: String,
    val photoUri: String
)

class KolodaSampleAdapter(val context: Context, val data: List<SwypedUserCard>?) : BaseAdapter() {

    private val dataList = mutableListOf<SwypedUserCard>()

    init {
        if (data != null) {
            dataList.addAll(data)
        }
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): SwypedUserCard {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<SwypedUserCard>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: DataViewHolder
        var view = convertView

        if (view == null) {
            val binding = parent.viewBinding(ItemUserFullCardBinding::inflate)
            view = binding.root
            holder = DataViewHolder(binding)
            view.tag = holder
        } else {
            holder = view.tag as DataViewHolder
        }

        holder.bindData(context, getItem(position))

        return view
    }

    class DataViewHolder(val binding: ItemUserFullCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bindData(context: Context, data: SwypedUserCard) {
            with(binding) {
                ivAvatar.load(data.photoUri) {
                    transformations(CircleCropTransformation())
                }
                ivAvatarBlured.load(data.photoUri) {
                    transformations(
                        BlurTransformation(
                            context, 25F, 1F
                        )
                    )
                }
                tvUsername.text = data.name
            }
        }
    }
}
