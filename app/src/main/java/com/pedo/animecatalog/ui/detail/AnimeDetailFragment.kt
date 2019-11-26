package com.pedo.animecatalog.ui.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.pedo.animecatalog.databinding.FragmentAnimeDetailBinding

class AnimeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentAnimeDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val movie = AnimeDetailFragmentArgs.fromBundle(arguments!!).selectedMovie
        val viewModelFactory = AnimeDetailViewModelFactory(movie,application)
        binding.viewModel = ViewModelProviders.of(this,viewModelFactory).get(AnimeDetailViewModel::class.java)

        return binding.root
    }


}
