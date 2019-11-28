package com.pedo.animecatalog.ui.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.pedo.animecatalog.databinding.FragmentAnimeListBinding

class AnimeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAnimeListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.btnListAiring.setOnClickListener {
            navigateTo(AnimeListFragmentDirections.moveToAiring())
        }

        binding.btnListUpcoming.setOnClickListener {
            navigateTo(AnimeListFragmentDirections.moveToUpcoming())
        }

        binding.btnListByTypes.setOnClickListener {
            navigateTo(AnimeListFragmentDirections.moveToTypes())
        }

        binding.btnSearchBySeason.setOnClickListener {
            navigateTo(AnimeListFragmentDirections.moveToSeason())
        }

        return binding.root
    }

    fun navigateTo(destination : NavDirections){
        this.findNavController().navigate(destination)
    }
}
