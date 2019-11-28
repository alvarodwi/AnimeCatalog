package com.pedo.animecatalog.ui.list.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedo.animecatalog.databinding.FragmentAnimeUpcomingBinding
import com.pedo.animecatalog.ui.list.AnimeListViewModel
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter

class AnimeUpcomingFragment : Fragment() {
    private val viewModel: AnimeListViewModel by lazy {
        ViewModelProviders.of(
            this,
            AnimeListViewModel.Factory("upcoming",activity!!.application)
        ).get(AnimeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(activity)
        val binding = FragmentAnimeUpcomingBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.animeUpcomingRv.layoutManager = LinearLayoutManager(activity.applicationContext)
        binding.animeUpcomingRv.adapter =
            AnimeListAdapter(
                AnimeListAdapter.OnClickListener {
                    viewModel.displayMovieDetail(it)
                })

        viewModel.navigateToDetail.observe(this, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeUpcomingFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        viewModel.status.observe(this, Observer {
            it?.let{ status ->
                when(status){
                    AnimeListingStatus.LOADING -> binding.srlUpcoming.isRefreshing = true
                    AnimeListingStatus.DONE, AnimeListingStatus.ERROR -> binding.srlUpcoming.isRefreshing = false
                }
            }
        })

        return binding.root
    }
}
