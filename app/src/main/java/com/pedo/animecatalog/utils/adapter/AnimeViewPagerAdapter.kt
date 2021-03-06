package com.pedo.animecatalog.utils.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pedo.animecatalog.R
import com.pedo.animecatalog.ui.list.types.movies.AnimeMoviesFragment
import com.pedo.animecatalog.ui.list.types.ovas.AnimeOVAsFragment
import com.pedo.animecatalog.ui.list.types.series.AnimeSeriesFragment
import com.pedo.animecatalog.ui.list.types.specials.AnimeSpecialsFragment

class AnimeViewPagerAdapter(private val context : Context, fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val tabTitles = intArrayOf(R.string.series_text,R.string.movies_text,R.string.ova_text,R.string.specials_text)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AnimeSeriesFragment()
            1 -> fragment = AnimeMoviesFragment()
            2 -> fragment = AnimeOVAsFragment()
            3 -> fragment = AnimeSpecialsFragment()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }
}