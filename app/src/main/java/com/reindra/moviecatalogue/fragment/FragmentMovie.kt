package com.reindra.moviecatalogue.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.reindra.moviecatalogue.MovieItems
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.activity.DetailActivity
import com.reindra.moviecatalogue.adapter.MovieAdapter
import com.reindra.moviecatalogue.api.MovieViewModel
import kotlinx.android.synthetic.main.fragment_fragment_movie.*
import kotlinx.android.synthetic.main.item_movie.*

class FragmentMovie : Fragment() {
    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieItems: MovieItems
//    lateinit var btndescription : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.setHasFixedSize(true)
        showRecyclerView()
    }

    private fun showRecyclerView() {
        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)

        movieViewModel.setMovies()
        showLoading(true)

        movieViewModel.getmovies().observe(this, Observer { movieItems ->
            if (movieItems != null) {
                adapter.setData(movieItems)
                showLoading(false)

            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
