package com.pedo.animecatalog.ui.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedo.animecatalog.databinding.FragmentAnimeFavoriteBinding
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.TYPE_GRID
import com.pedo.animecatalog.utils.TYPE_LIST
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import com.pedo.animecatalog.utils.determineGridSpan
import com.pedo.animecatalog.MainViewModel

class AnimeFavoriteFragment : Fragment() {
    private val viewModel: AnimeFavoriteViewModel by lazy {
        ViewModelProvider(
            this,
            AnimeFavoriteViewModel.Factory("tv", activity!!.application)
        ).get(AnimeFavoriteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(activity)
        val binding = FragmentAnimeFavoriteBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val animeAdapter = AnimeListAdapter(
            AnimeListAdapter.OnClickListener {
                viewModel.displayMovieDetail(it)
            })
        binding.animeFavoriteRv.adapter = animeAdapter
        val linearLayoutManager = LinearLayoutManager(activity.applicationContext)
        val gridLayoutManager = GridLayoutManager(
            activity.applicationContext,
            determineGridSpan(activity.applicationContext)
        )

        val mainVM = ViewModelProvider(activity).get(MainViewModel::class.java)

        mainVM.viewMode.observe(viewLifecycleOwner, Observer { viewMode ->
            viewMode?.let {
                when (it) {
                    TYPE_LIST -> {
                        binding.animeFavoriteRv.layoutManager = linearLayoutManager
                        animeAdapter.setAdapterViewMode(TYPE_LIST)
                    }
                    TYPE_GRID -> {
                        binding.animeFavoriteRv.layoutManager = gridLayoutManager
                        animeAdapter.setAdapterViewMode(TYPE_GRID)
                    }
                }
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeFavoriteFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        binding.srlFavorites.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    AnimeListingStatus.LOADING -> binding.srlFavorites.isRefreshing = true
                    AnimeListingStatus.DONE, AnimeListingStatus.ERROR -> binding.srlFavorites.isRefreshing =
                        false
                }
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.onRefresh()
    }
}
