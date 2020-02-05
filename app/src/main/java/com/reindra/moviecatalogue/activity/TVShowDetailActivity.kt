package com.reindra.moviecatalogue.activity

import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.model.DetailModel
import com.reindra.moviecatalogue.model.TV
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.util.CategoryEnum
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class TVShowDetailActivity : AppCompatActivity() {

    private lateinit var moviesModel: DetailModel
    private lateinit var trailerKey: String
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_FILM = "EXTRA_FILM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val item = intent.getParcelableExtra<TV>(EXTRA_FILM)
        toolbar_title.text = item.name
        iv_poster.z = 5f

        val year = DateFormat.format("yyyy", SimpleDateFormat("yyyy-MM-dd").parse(item.firsAirDate))
        tv_tittle.text = item.name
        tv_year.text = year
        tv_synopsis.text = item.synopsis
        val rate = (item.rate.toFloat() * 10).toInt()
        tv_score.text = String.format("%s%%", rate)
        val score = rate / 20f
        rb_score.rating = score
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185${item.poster}")
            .placeholder(R.drawable.img_placeholder)
            .into(iv_poster)

        moviesModel = ViewModelProviders.of(this).get(DetailModel::class.java)
        moviesModel.onViewAttached(item.id, CategoryEnum.TV.value)
        moviesModel.getFavorite().observe(this, getFavorite)

        btn_favorite.setOnClickListener {
            val fav = Favorite(
                id = item.id,
                title = item.name,
                date = item.firsAirDate,
                rate = item.rate,
                synopsis = item.synopsis,
                poster = item.poster,
                category = CategoryEnum.TV.value
            )
            if (isFavorite) {
                moviesModel.deleteFavorite(fav)
            } else {
                moviesModel.insertFavorite(fav)
            }
        }
    }

    private val getFavorite = object : Observer<List<Favorite>?> {
        override fun onChanged(listFav: List<Favorite>?) {
            if (listFav != null && listFav.size > 0) {
                isFavorite = true
                val ivAnimation =
                    AnimatedVectorDrawableCompat.create(iv_heart.context, R.drawable.ic_heart_anim)
                iv_heart.setImageDrawable(ivAnimation)
                ivAnimation?.start()
                iv_heart.imageTintList = getColorStateList(R.color.red)
            } else {
                iv_heart.setImageDrawable(getDrawable(R.drawable.ic_heart))
                iv_heart.imageTintList = getColorStateList(R.color.grey)
                isFavorite = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}
