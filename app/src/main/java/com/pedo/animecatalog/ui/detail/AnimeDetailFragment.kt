package com.pedo.animecatalog.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pedo.animecatalog.R
import com.pedo.animecatalog.databinding.FragmentAnimeDetailBinding

class AnimeDetailFragment : Fragment() {
    private lateinit var viewModel : AnimeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentAnimeDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val movie = AnimeDetailFragmentArgs.fromBundle(arguments!!).selectedMovie
        val viewModelFactory = AnimeDetailViewModelFactory(movie,application)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(AnimeDetailViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.isFavorited.observe(this, Observer {
            it?.let{favorited ->
                if (favorited){
                    binding.btnAddFavorite.text = getString(R.string.remove_fav)
                    binding.btnAddFavorite.setOnClickListener {
                        viewModel.unMarkFavorite()
                    }
                }else{
                    binding.btnAddFavorite.text = getString(R.string.add_fav)
                    binding.btnAddFavorite.setOnClickListener {
                        viewModel.markFavorite()
                    }
                }
            }
        })

        return binding.root
    }


}
