package com.reindra.moviecatalogue.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.reindra.moviecatalogue.MovieItems
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.activity.DetailActivity
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_tv.view.*

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {
    private val mDataTv = ArrayList<MovieItems>()

    fun setDataTv(items: ArrayList<MovieItems>) {
        mDataTv.clear()
        mDataTv.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false)
        return ViewHolder(mView)
    }

    override fun getItemCount(): Int = mDataTv.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mDataTv[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieItems: MovieItems) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(movieItems.poster_path)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_photo)
                tvtitle.text = movieItems.title
                tvdescriptiontv.text = movieItems.overview
                itemView.setOnClickListener {
                    var intent = Intent(context, DetailActivity::class.java)
                    intent = intent.putExtra(DetailActivity.FLAG_EXTRA, movieItems)
                    context.startActivities(arrayOf(intent))
                }

            }
        }
    }

}