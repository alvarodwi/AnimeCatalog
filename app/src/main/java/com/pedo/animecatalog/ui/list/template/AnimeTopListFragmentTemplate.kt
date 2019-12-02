package com.pedo.animecatalog.ui.list.template

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
import com.pedo.animecatalog.MainViewModel
import com.pedo.animecatalog.databinding.FragmentAnimeTopListBinding
import com.pedo.animecatalog.ui.list.types.airing.AnimeAiringFragmentDirections
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.TYPE_GRID
import com.pedo.animecatalog.utils.TYPE_LIST
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import com.pedo.animecatalog.utils.determineGridSpan
import timber.log.Timber

abstract class AnimeTopListFragmentTemplate(subType: String) : Fragment() {
    private lateinit var binding: FragmentAnimeTopListBinding
    private lateinit var animeAdapter : AnimeListAdapter

    private val viewModel: AnimeTopListViewModelTemplate by lazy {
        ViewModelProvider(
            this,
            AnimeTopListViewModelTemplate.Factory(
                subType,
                activity!!.application
            )
        ).get(AnimeTopListViewModelTemplate::class.java)
    }

    private val mainVM: MainViewModel by lazy {
        ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeTopListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        animeAdapter = AnimeListAdapter(
            AnimeListAdapter.OnClickListener {
                viewModel.displayMovieDetail(it)
            })
        binding.animeTopListRv.adapter = animeAdapter

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeAiringFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        binding.srlTopList.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    AnimeListingStatus.LOADING -> binding.srlTopList.isRefreshing = true
                    AnimeListingStatus.DONE, AnimeListingStatus.ERROR -> binding.srlTopList.isRefreshing =
                        false
                }
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.animeTopListRv.apply {
            Timber.d("View Mode Value : ${mainVM.viewMode.value}")
            when (mainVM.viewMode.value) {
                TYPE_GRID -> {
                    this.layoutManager = GridLayoutManager(
                        requireContext(),
                        determineGridSpan(requireContext())
                    )
                    animeAdapter.setAdapterViewMode(TYPE_GRID)
                }
                TYPE_LIST -> {
                    this.layoutManager = LinearLayoutManager(requireContext())
                    animeAdapter.setAdapterViewMode(TYPE_LIST)
                }
            }
        }

        mainVM.viewMode.observe(viewLifecycleOwner, Observer { viewMode ->
            viewMode?.let {
                if (mainVM.hasViewModeChanged(viewModel.oldViewMode.value)) {
                    Timber.d("Layout Manager : ${binding.animeTopListRv.layoutManager}")
                    when (it) {
                        TYPE_LIST -> {
                            binding.animeTopListRv.layoutManager = LinearLayoutManager(requireContext())
                            animeAdapter.setAdapterViewMode(TYPE_LIST)
                        }
                        TYPE_GRID -> {
                            binding.animeTopListRv.layoutManager = GridLayoutManager(
                                requireContext(),
                                determineGridSpan(requireContext())
                            )
                            animeAdapter.setAdapterViewMode(TYPE_GRID)
                        }
                    }
                }
                viewModel.setOldData(viewMode)
            }
        })
    }
}