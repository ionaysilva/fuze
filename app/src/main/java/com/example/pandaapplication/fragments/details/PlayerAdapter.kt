package com.example.pandaapplication.fragments.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.pandaapplication.R
import com.example.pandaapplication.core.glide.GlideApp
import com.example.pandaapplication.core.utils.Constants
import com.example.pandaapplication.databinding.ItemMatchPlayerLeftBinding
import com.example.pandaapplication.databinding.ItemMatchPlayerRightBinding
import com.example.pandaapplication.model.Player


class PlayerAdapter(private val team: Int = Constants.TEAM_A) : RecyclerView.Adapter<ItemViewHolder<Player>>() {
    private var items = mutableListOf<Player>()
    private var parent: ViewGroup? = null

    fun setList(players: List<Player>) {
        this.items = players.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<Player> {
        val inflater = LayoutInflater.from(parent.context)
        this.parent = parent
        return when (viewType) {
            Constants.TEAM_A -> {
                val binding= ItemMatchPlayerLeftBinding.inflate(inflater, parent, false)
                PlayerLeftViewHolder(binding)
            }

            Constants.TEAM_B -> {
                val binding= ItemMatchPlayerRightBinding.inflate(inflater, parent, false)
                PlayerRightViewHolder(binding)
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder<Player>, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (team == Constants.TEAM_A) Constants.TEAM_A else Constants.TEAM_B
    }

}
abstract class ItemViewHolder<Player>(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: Player)
}

class PlayerLeftViewHolder(private val binding: ItemMatchPlayerLeftBinding) :
    ItemViewHolder<Player>(binding) {

    override fun bind(item: Player) {
        binding.name.text = item.first_name + item.last_name
        binding.nick.text = item.name
        GlideApp.with(itemView.context)
            .load(item.image_url)
            .error(R.drawable.rectangle_5)
            .into(binding.firstImage)
    }

}

class PlayerRightViewHolder(private val binding: ItemMatchPlayerRightBinding) :
    ItemViewHolder<Player>(binding) {

    override fun bind(item: Player) {
        binding.name.text = item.first_name + item.last_name
        binding.nick.text = item.name
        GlideApp.with(itemView.context)
            .load(item.image_url)
            .error(R.drawable.rectangle_5)
            .into(binding.firstImage)
    }

}