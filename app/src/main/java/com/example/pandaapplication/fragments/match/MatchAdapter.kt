package com.example.pandaapplication.fragments.match

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.pandaapplication.R
import com.example.pandaapplication.core.glide.GlideApp
import com.example.pandaapplication.databinding.ItemMatchBinding
import com.example.pandaapplication.databinding.ItemMatchLoadingBinding
import com.example.pandaapplication.extensions.loadTextDate
import com.example.pandaapplication.model.MatchesItem
import com.example.pandaapplication.model.Status
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val LOADING = 0
const val ITEM = 1

class MatchAdapter(
    private val onClickListener: (MatchesItem?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itens = mutableListOf<MatchesItem?>()
    private var parent: ViewGroup? = null
    private var isLoading = false

    fun setMatchList(matches: List<MatchesItem?>) {
        this.itens = matches.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<MatchesItem?> {
        val inflater = LayoutInflater.from(parent.context)
        this.parent = parent
        return when (viewType) {
            ITEM -> {
                val binding: ItemMatchBinding = ItemMatchBinding.inflate(inflater, parent, false)
                MatchViewHolder(binding, onClickListener)
            }

            LOADING -> {
                val binding: ItemMatchLoadingBinding =
                    ItemMatchLoadingBinding.inflate(inflater, parent, false)
                MatchLoadingViewHolder(binding)
            }

            else -> throw IllegalArgumentException()
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itens[position]
        when (holder) {
            is MatchViewHolder -> {
                holder.bind(item)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == itens.size - 1 && isLoading) LOADING else ITEM
    }

    override fun getItemCount(): Int {
        return itens.size
    }

    fun addLoadingFooter() {
        isLoading = true
        add()
    }

    fun removeLoadingFooter() {
        isLoading = false
        val position: Int = itens.size - 1
        val result = getItem(position)
        if (result != null) {
            itens.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun add() {
        itens.add(null)
        notifyItemInserted(itens.size - 1)
    }

    fun addAll(matches: List<MatchesItem?>) {
        val size = itens.size
        itens.addAll(matches)
        notifyItemRangeInserted(size, itens.size)
    }

    private fun getItem(position: Int): MatchesItem? {
        return itens[position]
    }

}


abstract class ItemViewHolder<T>(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}

class MatchViewHolder(private val binding: ItemMatchBinding, private val onClickListener: (MatchesItem?) -> Unit) : ItemViewHolder<MatchesItem?>(binding) {

    override fun bind(item: MatchesItem?) {
        item?.let { match ->
            binding.leagueName.text = match.league.name
            binding.root.setOnClickListener {
                onClickListener.invoke(match)
            }
            if (match.opponents.isNotEmpty()) {
                match.opponents[0].let {
                    binding.firstTitle.text = it.opponent.name
                    GlideApp.with(binding.root.context)
                        .load(it.opponent.image_url)
                        .error(R.drawable.team_logo)
                        .into(binding.firstImage)
                }
                if (match.opponents.size == 2) {
                    match.opponents[1].let {
                        binding.secondTitle.text = it.opponent.name
                        GlideApp.with(binding.root.context)
                            .load(it.opponent.image_url)
                            .error(R.drawable.team_logo)
                            .into(binding.secondImage)
                    }
                }
            }

            if (matchIsRunning(match.status)) {
                binding.gameRunning.frame.setBackgroundResource(R.drawable.backgroud_item)
                binding.gameRunning.text.text = itemView.context.getText(R.string.now)
            } else {
                binding.gameRunning.frame.setBackgroundResource(R.drawable.backgroud_item_scheduled)
                binding.gameRunning.text.text = match.begin_at.loadTextDate()
            }

            GlideApp.with(itemView.context)
                .load(match.league.image_url)
                .error(R.drawable.league_logo)
                .into(binding.leagueLogo)


        }
    }

    private fun matchIsRunning(status: String): Boolean {
        return status == Status.RUNNING.statusName
    }

}

class MatchLoadingViewHolder(private val binding: ItemMatchLoadingBinding) :ItemViewHolder<MatchesItem?>(binding){
    override fun bind(item: MatchesItem?) {

    }

}