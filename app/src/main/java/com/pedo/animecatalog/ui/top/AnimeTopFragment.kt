package com.pedo.animecatalog.ui.top


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
import com.google.android.material.snackbar.Snackbar
import com.pedo.animecatalog.MainViewModel
import com.pedo.animecatalog.databinding.FragmentAnimeTopBinding
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.TYPE_GRID
import com.pedo.animecatalog.utils.TYPE_LIST
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter
import com.pedo.animecatalog.utils.determineGridSpan
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class AnimeTopFragment : Fragment() {
    private lateinit var binding: FragmentAnimeTopBinding
    private lateinit var animeAdapter : AnimeListAdapter

    private val viewModel: AnimeTopViewModel by lazy {
        ViewModelProvider(
            this,
            AnimeTopViewModel.Factory(activity!!.application)
        ).get(AnimeTopViewModel::class.java)
    }

    private val mainVM: MainViewModel by lazy {
        ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("ON VIEW")
        binding = FragmentAnimeTopBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        animeAdapter = AnimeListAdapter(
            AnimeListAdapter.OnClickListener {
                viewModel.displayMovieDetail(it)
            })
        binding.animeTopRv.adapter = animeAdapter

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeTopFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        binding.srlTop.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let { status ->
                when (status) {
                    AnimeListingStatus.LOADING -> binding.srlTop.isRefreshing = true
                    AnimeListingStatus.DONE -> binding.srlTop.isRefreshing = false
                    AnimeListingStatus.ERROR -> {
                        binding.srlTop.isRefreshing = false
                        Snackbar.make(view!!, "Cannot Refresh Network Data", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.animeTopRv.apply {
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
                    Timber.d("Layout Manager : ${binding.animeTopRv.layoutManager}")
                    when (it) {
                        TYPE_LIST -> {
                            binding.animeTopRv.layoutManager = LinearLayoutManager(requireContext())
                            animeAdapter.setAdapterViewMode(TYPE_LIST)
                        }
                        TYPE_GRID -> {
                            binding.animeTopRv.layoutManager = GridLayoutManager(
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
