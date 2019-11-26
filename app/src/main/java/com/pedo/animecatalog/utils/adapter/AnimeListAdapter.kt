package com.pedo.animecatalog.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedo.animecatalog.databinding.AnimeItemBinding
import com.pedo.animecatalog.domain.Anime

class AnimeListAdapter(private val onClickListener: OnClickListener) : ListAdapter<Anime, AnimeListAdapter.AnimeViewHolder>(
    DiffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeViewHolder {
        return AnimeViewHolder(
            AnimeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie,onClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Anime>(){
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class AnimeViewHolder(private val binding : AnimeItemBinding) : RecyclerView.ViewHolder(binding.root){
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