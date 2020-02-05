package com.reindra.moviecatalogue.adapter

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
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.model.TV
import com.reindra.moviecatalogue.model.TVShowsModel
import com.reindra.moviecatalogue.util.CategoryEnum
import kotlinx.android.synthetic.main.fragment_fragment_tv_show.*


class FavoriteTVShowFragment : androidx.fragment.app.Fragment() {

    private lateinit var tvShowsModel: TVShowsModel
    private lateinit var adapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_fragment_tv_show, container, false)

        activity?.window?.setSharedElementExitTransition(
            TransitionInflater.from(context).inflateTransition(
                R.transition.element_transition
            )
        )

        adapter = TvShowAdapter(activity!!, favListener = { movie, ivHeart, isFavorite ->
            val fav = Favorite(
                id = movie.id,
                title = movie.name,
                date = movie.firsAirDate,
                rate = movie.rate,
                synopsis = movie.synopsis,
                poster = movie.poster,
                category = CategoryEnum.TV.value
            )
            if (isFavorite) {
                tvShowsModel.deleteFavorite(fav)
                ivHeart.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                ivHeart.imageTintList = context?.getColorStateList(R.color.grey)
                Toast.makeText(context, getString(R.string.deleteitem)+" "+movie.name, Toast.LENGTH_SHORT).show()
                adapter.removeFavorite(fav)
            } else {
                tvShowsModel.insertFavorite(fav)
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

        tvShowsModel = ViewModelProviders.of(this).get(TVShowsModel::class.java)
        tvShowsModel.onViewAttached()
        tvShowsModel.getAllFavorites().observe(this, getFavorite)
        tv_no_datatv.text = resources.getString(R.string.empty)

    }

    private val getFavorite = object : Observer<List<Favorite>?> {
        override fun onChanged(listFav: List<Favorite>?) {
            if (listFav != null) {
                adapter.setFavorites(listFav)
                val listTV: MutableList<TV> = mutableListOf()
                listFav.forEach {
                    listTV.add(
                        TV(
                            id = it.id,
                            name = it.title,
                            firsAirDate = it.date,
                            rate = it.rate,
                            synopsis = it.synopsis,
                            poster = it.poster
                        )
                    )
                }
                adapter.setData(listTV)
                view_no_data.visibility = if(adapter.itemCount>0) View.GONE else View.VISIBLE
            }

        }
    }

    private fun showRecyclerCardView() {
        rv_category.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rv_category.adapter = adapter
    }
}

