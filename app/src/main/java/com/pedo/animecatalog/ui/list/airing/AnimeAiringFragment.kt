package com.pedo.animecatalog.ui.list.airing


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedo.animecatalog.databinding.FragmentAnimeAiringBinding
import com.pedo.animecatalog.ui.list.AnimeListViewModel
import com.pedo.animecatalog.utils.AnimeListingStatus.*
import com.pedo.animecatalog.utils.adapter.AnimeListAdapter

class AnimeAiringFragment : Fragment() {
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
        val binding = FragmentAnimeAiringBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.animeAiringRv.layoutManager = LinearLayoutManager(activity.applicationContext)
        binding.animeAiringRv.adapter =
            AnimeListAdapter(
                AnimeListAdapter.OnClickListener {
                    viewModel.displayMovieDetail(it)
                })

        viewModel.navigateToDetail.observe(this, Observer { anime ->
            anime?.let {
                this.findNavController().navigate(
                    AnimeAiringFragmentDirections.showDetail(it)
                )
                viewModel.displayMovieDetailCompleted()
            }
        })

        binding.srlAiring.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.status.observe(this, Observer {
            it?.let{ status ->
                when(status){
                    LOADING -> binding.srlAiring.isRefreshing = true
                    DONE,ERROR -> binding.srlAiring.isRefreshing = false
                }
            }
        })

        return binding.root
    }

}
