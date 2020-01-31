package com.reindra.moviecatalogue.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.reindra.moviecatalogue.MovieItems
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class TvViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "d79bcee1a179a353671dc2def12adc03"
    }

    val currentLangueage = Locale.getDefault().getDisplayLanguage()
    val listtv = MutableLiveData<ArrayList<MovieItems>>()
    internal fun setTV() {
        // request API
        val client = AsyncHttpClient()
        val listItemTvs = ArrayList<MovieItems>()
        val url =
            "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"
        if (currentLangueage.equals("INDONESIA")) {
            val url =
                "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=id-ID";
        } else if (currentLangueage.equals("ENGLISH")) {
            val ulr =
                "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US";
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
                        val tv = list.getJSONObject(i)
                        val TvItems = MovieItems()
                        TvItems.id = tv.getString("id")
                        TvItems.poster_path =
                            "http://image.tmdb.org/t/p/w185" + tv.getString("poster_path")
                        TvItems.title = tv.getString("name")
                        TvItems.overview = tv.getString("overview")

                        listItemTvs.add(TvItems)
                    }
                    listtv.postValue(listItemTvs)
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

    internal fun gettv(): LiveData<ArrayList<MovieItems>> {
        return listtv
    }
}