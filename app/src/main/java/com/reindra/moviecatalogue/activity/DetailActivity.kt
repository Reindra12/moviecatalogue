package com.reindra.moviecatalogue.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.reindra.moviecatalogue.MovieItems
import com.reindra.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.view.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val FLAG_EXTRA = "flag_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieItems = intent.getParcelableExtra(FLAG_EXTRA) as MovieItems
        val title = movieItems.title
        val overview = movieItems.overview

        tvdetaildescription.text = overview
        tvdetailjudul.text = title
        Glide.with(this)
            .load(movieItems.poster_path)
            .into(imgdetailposter)
    }
}

