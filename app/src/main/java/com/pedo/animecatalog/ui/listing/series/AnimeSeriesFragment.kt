package com.pedo.animecatalog.ui.listing.series


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.pedo.animecatalog.databinding.FragmentAnimeSeriesBinding
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import com.pedo.animecatalog.ui.listing.AnimeListFragmentDirections
import com.pedo.animecatalog.ui.listing.AnimeListViewModel

/**
 * A simple [Fragment] subclass.
 */
class AnimeSeriesFragment : Fragment() {
    private val viewModel: AnimeListViewModel by lazy {
        ViewModelProviders.of(
            this,
            AnimeListViewModel.Factory("tv",activity!!.application)
        ).get(AnimeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(activity)
        val binding = FragmentAnimeSeriesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.animeSeriesRv.layoutManager = LinearLayoutManager(activity.applicationContext)
        binding.animeSeriesRv.adapter =
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
