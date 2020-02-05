package com.reindra.moviecatalogue.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.fragment.FavoriteMovieFragment

class FavoriteViewPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    private var title: List<String> = listOf(
        mContext.resources.getString(R.string.tab_text_1),
        mContext.resources.getString(R.string.tab_text_2)
    )

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return FavoriteMovieFragment()
            }
            1 -> {
                return FavoriteTVShowFragment()
            }
            else -> return FavoriteMovieFragment()
        }
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}  