package com.reindra.moviecatalogue.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.reindra.moviecatalogue.MovieItems
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.activity.DetailActivity
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val mData = ArrayList<MovieItems>()
    fun setData(items: ArrayList<MovieItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(mView)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieItems: MovieItems) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(movieItems.poster_path)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgposter)
                tvjudul.text = movieItems.title
                tvdescription.text = movieItems.overview
                btndescription.setOnClickListener {
                    var intent = Intent(context, DetailActivity::class.java)
                    intent = intent.putExtra(DetailActivity.FLAG_EXTRA, movieItems)
                    context.startActivities(arrayOf(intent))

                }

            }
        }
    }
}