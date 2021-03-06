package com.reindra.moviecatalogue.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.adapter.MovieAdapter
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.model.MovieItems
import com.reindra.moviecatalogue.model.MovieViewModel
import com.reindra.moviecatalogue.util.CategoryEnum
import kotlinx.android.synthetic.main.fragment_fragment_movie.*

class FragmentMovie : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_fragment_movie, container, false)

        adapter = MovieAdapter(activity!!, favListener = { movie, ivHeart, isFavorite ->
            val fav = Favorite(
                id = movie.id,
                title = movie.title,
                date = movie.releaseDate,
                rate = movie.rate,
                synopsis = movie.synopsis,
                poster = movie.poster,
                category = CategoryEnum.MOVIE.value
            )
            if (isFavorite) {
                movieViewModel.deleteFavorite(fav)
                ivHeart.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                ivHeart.imageTintList = context?.getColorStateList(R.color.grey)
                adapter.removeFavorite(fav)
            } else {
                movieViewModel.insertFavorite(fav)
                adapter.addFavorite(fav)
                context?.let {
                    val ivAnimation =
                        AnimatedVectorDrawableCompat.create(it, R.drawable.ic_heart_anim)
                    ivHeart.setImageDrawable(ivAnimation)
                    ivAnimation?.start()
                }

            }
        })
        adapter.notifyDataSetChanged()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerCardView()
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.onViewAttached()
        movieViewModel.getAllFavorites().observe(this, getFavorite)
        movieViewModel.getMovies().observe(this, getMovie)
        movieViewModel.setMovies()
        progressBar.visibility = View.VISIBLE
    }

    private val getFavorite = object : Observer<List<Favorite>?> {
        override fun onChanged(listFav: List<Favorite>?) {
            if (listFav != null) {
                adapter.setFavorites(listFav)
            }
        }
    }
    private val getMovie = object : Observer<List<MovieItems>?> {
        override fun onChanged(listMovie: List<MovieItems>?) {
            if (listMovie != null) {
                adapter.setData(listMovie)
                progressBar.visibility = View.GONE
                Log.d("MovieFragment", "$listMovie")
            }
        }
    }

    private fun showRecyclerCardView() {
        rv_category.layoutManager = GridLayoutManager(context, 2)
        rv_category.adapter = adapter
    }

}

