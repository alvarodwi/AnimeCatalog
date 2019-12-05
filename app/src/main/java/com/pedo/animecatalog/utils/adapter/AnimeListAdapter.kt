package com.pedo.animecatalog.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedo.animecatalog.databinding.AnimeGridItemBinding
import com.pedo.animecatalog.databinding.AnimeItemBinding
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.utils.GRID_ITEM
import com.pedo.animecatalog.utils.LIST_ITEM
import com.pedo.animecatalog.utils.TYPE_GRID
import com.pedo.animecatalog.utils.TYPE_LIST

class AnimeListAdapter(private val onClickListener: OnClickListener, private var viewMode: String = TYPE_LIST) : ListAdapter<Anime, AnimeListAdapter.BaseAnimeViewHolder<*>>(
    DiffCallback
){
    fun setAdapterViewMode(viewMode: String){
        this.viewMode = viewMode
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseAnimeViewHolder<*> {
        return when(viewType){
            LIST_ITEM -> {
                val binding = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AnimeListViewHolder(binding)
            }
            GRID_ITEM -> {
                val binding = AnimeGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AnimeGridViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: BaseAnimeViewHolder<*>, position: Int) {
        val anime = getItem(position)
        when(holder){
            is AnimeListViewHolder -> holder.bind(anime,onClickListener)
            is AnimeGridViewHolder -> holder.bind(anime,onClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(viewMode){
            TYPE_LIST -> LIST_ITEM
            TYPE_GRID -> GRID_ITEM
            else -> throw IllegalArgumentException("Wrong Type!")
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Anime>(){
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.id == newItem.id
        }

    }

    //base view holder
    abstract class BaseAnimeViewHolder<T>(itemView : View) : RecyclerView.ViewHolder(itemView)

    class AnimeListViewHolder(val binding : AnimeItemBinding) : BaseAnimeViewHolder<AnimeItemBinding>(binding.root){
        fun bind(anime : Anime,itemClick : OnClickListener){
            binding.anime = anime
            binding.onClickListener = itemClick
            binding.executePendingBindings()
        }
    }

    class AnimeGridViewHolder(val binding : AnimeGridItemBinding) : BaseAnimeViewHolder<AnimeGridItemBinding>(binding.root){
        fun bind(anime : Anime,itemClick : OnClickListener){
            binding.anime = anime
            binding.onClickListener = itemClick
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (anime: Anime) -> Unit){
        fun onClick(anime: Anime) = clickListener(anime)
    }
}