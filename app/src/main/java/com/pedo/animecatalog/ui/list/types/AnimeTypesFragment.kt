package com.pedo.animecatalog.ui.list.types


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pedo.animecatalog.databinding.FragmentAnimeTypesBinding
import com.pedo.animecatalog.utils.adapter.AnimeViewPagerAdapter

/**
 * A simple [Fragment] subclass.
 */
class AnimeTypesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(activity)
        val binding = FragmentAnimeTypesBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val animeViewPager = AnimeViewPagerAdapter(activity.applicationContext,childFragmentManager)
        binding.animeViewPager.adapter = animeViewPager
        binding.animeTabLayout.setupWithViewPager(binding.animeViewPager)

        return binding.root
    }

}
