package com.reindra.moviecatalogue.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.activity.DetailActivity
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.model.MovieItems
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(
    val activity: Activity,
    val favListener: (MovieItems, ImageView, Boolean) -> Unit
) : RecyclerView.Adapter<MovieAdapter.CardViewViewHolder>() {

    private val mData = mutableListOf<MovieItems>()
    private val listFavorites = mutableListOf<Favorite>()

    fun setData(items: List<MovieItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun setFavorites(items: List<Favorite>) {
        listFavorites.clear()
        listFavorites.addAll(items)
        notifyDataSetChanged()
    }

    fun addFavorite(item: Favorite) {
        listFavorites.add(item)
    }

    fun removeFavorite(item: Favorite) {
        listFavorites.remove(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val p = mData[position]
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w185${p.poster}")
            .placeholder(R.drawable.img_placeholder)
            .into(holder.itemView.imgposter)
        holder.itemView.tvjudul.text = p.title
        holder.itemView.tv_description.text = p.synopsis
        val rate = (p.rate.toFloat() * 10).toInt()
        holder.itemView.tv_score_movie.text = String.format("%s%%", rate)
        holder.itemView.rb_score_movie.rating = rate / 20f
        if (listFavorites.any { it.id == p.id }) holder.itemView.iv_heartmovie.imageTintList =
            activity.getColorStateList(R.color.red)

        holder.itemView.cv_item_movie.setOnClickListener {

            val p1 = Pair.create<View, String>(holder.itemView.cv_item_movie, "container")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1)

            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_FILM, p)
            it.context.startActivity(intent, options.toBundle())
        }

        holder.itemView.iv_heartmovie.setOnClickListener {
            favListener(p, holder.itemView.iv_heartmovie, listFavorites.any { it.id == p.id })
        }

    }

    inner class CardViewViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    }
}
