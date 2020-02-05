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
import com.reindra.moviecatalogue.activity.TVShowDetailActivity
import com.reindra.moviecatalogue.model.TV
import com.reindra.moviecatalogue.entity.Favorite
import kotlinx.android.synthetic.main.item_tv.view.*

class TvShowAdapter (val activity: Activity, val favListener: (TV, ImageView, Boolean) -> Unit) : RecyclerView.Adapter<TvShowAdapter.CardViewViewHolder>() {

    private val mData = mutableListOf<TV>()
    private val listFavorites = mutableListOf<Favorite>()

    fun setData(items: List<TV>) {
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

    fun addItem(item: TV) {
        mData.add(item)
        notifyDataSetChanged()
    }


    fun clearData() {
        mData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false)
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
            .into(holder.itemView.img_photo)
        holder.itemView.tv_tittle.text = p.name
        holder.itemView.tvdescriptiontv.text = p.synopsis
        holder.itemView.tv_number.text = (position+1).toString()
        val rate = (p.rate.toFloat()*10).toInt()
        holder.itemView.tv_score.text = String.format("%s%%", rate)
        holder.itemView.rb_score.rating = rate/20f
        if(listFavorites.any { it.id == p.id }) holder.itemView.iv_heart.imageTintList = activity.getColorStateList(R.color.red)

        holder.itemView.cardView.setOnClickListener {

            val p1  = Pair.create<View, String>(holder.itemView.cardView, "container")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1)

            val intent = Intent(it.context, TVShowDetailActivity::class.java)
            intent.putExtra(TVShowDetailActivity.EXTRA_FILM, p)
            it.context.startActivity(intent, options.toBundle())
        }


        holder.itemView.btn_favorite.setOnClickListener {
            favListener(p, holder.itemView.iv_heart,listFavorites.any { it.id == p.id })
        }

    }

    inner class CardViewViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}
