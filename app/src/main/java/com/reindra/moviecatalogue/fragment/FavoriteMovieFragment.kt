package com.reindra.moviecatalogue.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.adapter.MovieAdapter
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.model.MovieItems
import com.reindra.moviecatalogue.model.MovieViewModel
import com.reindra.moviecatalogue.util.CategoryEnum
import kotlinx.android.synthetic.main.fragment_fragment_movie.*

class FavoriteMovieFragment : androidx.fragment.app.Fragment() {


    private lateinit var moviesViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_fragment_movie, container, false)

        activity?.window?.setSharedElementExitTransition(
            TransitionInflater.from(context).inflateTransition(
                R.transition.element_transition
            )
        )

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
                moviesViewModel.deleteFavorite(fav)
                ivHeart.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                ivHeart.imageTintList = context?.getColorStateList(R.color.grey)
                Toast.makeText(context, getString(R.string.deleteitem)+" "+movie.title, Toast.LENGTH_SHORT).show()
                adapter.removeFavorite(fav)
            } else {
                moviesViewModel.insertFavorite(fav)
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
        moviesViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        moviesViewModel.onViewAttached()
        moviesViewModel.getAllFavorites().observe(this, getFavorite)
        tv_no_data.text = resources.getString(R.string.empty)
    }

    private val getFavorite = object : Observer<List<Favorite>?> {
        override fun onChanged(listFav: List<Favorite>?) {
            if (listFav != null) {
                adapter.setFavorites(listFav)
                val listMovie: MutableList<MovieItems> = mutableListOf()
                listFav.forEach {
                    listMovie.add(
                        MovieItems(
                            id = it.id,
                            title = it.title,
                            releaseDate = it.date,
                            rate = it.rate,
                            synopsis = it.synopsis,
                            poster = it.poster
                        )
                    )
                }
                adapter.setData(listMovie)

            }
            view_no_data.visibility = if(adapter.itemCount>0) View.GONE else View.VISIBLE

        }
    }

    private fun showRecyclerCardView() {
        rv_category.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rv_category.adapter = adapter
    }
}
