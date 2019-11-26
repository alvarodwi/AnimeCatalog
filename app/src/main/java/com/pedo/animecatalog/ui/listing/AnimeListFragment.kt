package com.pedo.animecatalog.ui.listing


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pedo.animecatalog.databinding.FragmentAnimeListBinding
import com.pedo.animecatalog.utils.adapter.AnimeViewPagerAdapter

class AnimeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(activity)
        val binding = FragmentAnimeListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val animeViewPager = AnimeViewPagerAdapter(activity.applicationContext,childFragmentManager)
        binding.animeViewPager.adapter = animeViewPager
        binding.animeTabLayout.setupWithViewPager(binding.animeViewPager)

        return binding.root
    }


}
