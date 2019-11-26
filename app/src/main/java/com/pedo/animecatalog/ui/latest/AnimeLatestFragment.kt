package com.pedo.animecatalog.ui.latest


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.pedo.animecatalog.R
import com.pedo.animecatalog.databinding.FragmentAnimeMoviesBinding
import com.pedo.animecatalog.ui.listing.AnimeListFragmentDirections
import com.pedo.animecatalog.ui.listing.AnimeListViewModel
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import timber.log.Timber

class AnimeLatestFragment : Fragment() {
    private val viewModel: AnimeListViewModel by lazy {
        ViewModelProviders.of(
            this,
            AnimeListViewModel.Factory("airing",activity!!.application)
        ).get(AnimeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(activity)
        val binding = FragmentAnimeMoviesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.animeMoviesRv.layoutManager = LinearLayoutManager(activity.applicationContext)
        binding.animeMoviesRv.adapter =
            AnimeListAdapter(
                AnimeListAdapter.OnClickListener {
                    viewModel.displayMovieDetail(it)
                })

        viewModel.navigateToDetail.observe(this, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeListFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        return binding.root
    }

}
