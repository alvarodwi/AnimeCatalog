package com.pedo.animecatalog.ui.list.types.series


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedo.animecatalog.databinding.FragmentAnimeSeriesBinding
import com.pedo.animecatalog.ui.list.AnimeListViewModel
import com.pedo.animecatalog.ui.list.types.AnimeTypesFragmentDirections
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter

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
                    AnimeTypesFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        binding.srlSeries.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.status.observe(this, Observer {
            it?.let{ status ->
                when(status){
                    AnimeListingStatus.LOADING -> binding.srlSeries.isRefreshing = true
                    AnimeListingStatus.DONE, AnimeListingStatus.ERROR -> binding.srlSeries.isRefreshing = false
                }
            }
        })

        return binding.root
    }


}
