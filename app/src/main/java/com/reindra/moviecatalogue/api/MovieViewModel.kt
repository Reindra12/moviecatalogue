package com.reindra.moviecatalogue.api

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.reindra.moviecatalogue.MovieItems
import java.util.*
import kotlin.collections.ArrayList


class MovieViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "d79bcee1a179a353671dc2def12adc03"
    }

    val currentLanguage = Locale.getDefault().getDisplayLanguage()
    val listmovie = MutableLiveData<ArrayList<MovieItems>>()
    internal fun setMovies() {
        // request API
        val client = AsyncHttpClient()
        val listItems = ArrayList<MovieItems>()
        val url =
            "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"
        if (currentLanguage.equals("INDONESIA")) {
            val url =
                "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=id-ID"
        } else if (currentLanguage.equals("ENGLISH")) {
            val url =
                "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"
        }
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItems = MovieItems()
                        movieItems.id = movie.getString("id")
                        movieItems.poster_path =
                            "http://image.tmdb.org/t/p/w185" + movie.getString("poster_path")
                        movieItems.title = movie.getString("title")
                        movieItems.overview = movie.getString("overview")

                        listItems.add(movieItems)
                    }
                    listmovie.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }


    internal fun getmovies(): LiveData<ArrayList<MovieItems>> {
        return listmovie
    }
}







