package com.pedo.animecatalog.ui.list.season


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
import com.pedo.animecatalog.databinding.FragmentAnimeSeasonBinding
import com.pedo.animecatalog.ui.list.types.airing.AnimeAiringFragmentDirections
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.TYPE_GRID
import com.pedo.animecatalog.utils.TYPE_LIST
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import com.pedo.animecatalog.utils.determineGridSpan
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class AnimeSeasonFragment : Fragment() {
    private lateinit var binding: FragmentAnimeSeasonBinding
    private lateinit var animeAdapter : AnimeListAdapter

    private val viewModel: AnimeSeasonViewModel by lazy {
        ViewModelProvider(
            this,
            AnimeSeasonViewModel.Factory(
                activity!!.application
            )
        ).get(AnimeSeasonViewModel::class.java)
    }

    private val mainVM: MainViewModel by lazy {
        ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeSeasonBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        animeAdapter = AnimeListAdapter(
            AnimeListAdapter.OnClickListener {
                viewModel.displayMovieDetail(it)
            })
        binding.animeSeasonRv.adapter = animeAdapter

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeAiringFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        binding.srlSeason.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    AnimeListingStatus.LOADING -> binding.srlSeason.isRefreshing = true
                    AnimeListingStatus.DONE, AnimeListingStatus.ERROR -> binding.srlSeason.isRefreshing =
                        false
                }
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.animeSeasonRv.apply {
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
                    Timber.d("Layout Manager : ${binding.animeSeasonRv.layoutManager}")
                    when (it) {
                        TYPE_LIST -> {
                            binding.animeSeasonRv.layoutManager = LinearLayoutManager(requireContext())
                            animeAdapter.setAdapterViewMode(TYPE_LIST)
                        }
                        TYPE_GRID -> {
                            binding.animeSeasonRv.layoutManager = GridLayoutManager(
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
