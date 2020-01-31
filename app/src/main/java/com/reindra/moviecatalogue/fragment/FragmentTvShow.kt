package com.reindra.moviecatalogue.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reindra.moviecatalogue.R
import com.reindra.moviecatalogue.adapter.TvShowAdapter
import com.reindra.moviecatalogue.api.TvViewModel
import kotlinx.android.synthetic.main.fragment_fragment_tv_show.*

class FragmentTvShow : Fragment() {

    private lateinit var adapter: TvShowAdapter
    private lateinit var movieViewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewtv.setHasFixedSize(true)
        showrecyclerview()
    }

    private fun showrecyclerview() {
        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()
        recyclerViewtv.layoutManager = LinearLayoutManager(context)
        recyclerViewtv.adapter = adapter

        movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvViewModel::class.java)
        movieViewModel.setTV()
        showLoading(true)

        movieViewModel.gettv().observe(this, Observer { TvItems ->
            if (TvItems != null) {
                adapter.setDataTv(TvItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBartv.visibility = View.VISIBLE
        } else {
            progressBartv.visibility = View.GONE
        }
    }

}