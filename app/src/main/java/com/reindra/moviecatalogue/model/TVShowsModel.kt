package com.reindra.moviecatalogue.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.repository.AppDatabase
import com.reindra.moviecatalogue.repository.FavoriteRepository
import com.reindra.moviecatalogue.util.CategoryEnum
import com.reindra.moviecatalogue.util.ResponseCodeEnum
import cz.msebera.android.httpclient.Header

class TVShowsModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val API_KEY = "d79bcee1a179a353671dc2def12adc03"
    }

    private val listTv = MutableLiveData<List<TV>?>()
    private var db: AppDatabase? = null
    private lateinit var repository: FavoriteRepository
    private lateinit var allFavorite: LiveData<List<Favorite>>
    private val app = application

    internal fun onViewAttached() {
        db = AppDatabase.getAppDataBase(app)
        repository = FavoriteRepository(db!!.favoriteDao(), CategoryEnum.TV.value)
        allFavorite = repository.getAllFavorites()
    }

    internal fun setTVShows() {
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

        try {
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>?,
                    responseBody: ByteArray
                ) {
                    if (statusCode == ResponseCodeEnum.OK.code) {
                        val tvResponse =
                            Gson().fromJson(String(responseBody), TVResponse::class.java)
                        listTv.postValue(tvResponse.results)
                    } else {

                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>?,
                    responseBody: ByteArray?,
                    error: Throwable
                ) {
                    Log.d("onFailure", error.message)
                }
            })
        } catch (e: Exception) {
            Log.d("Exception", e.message)
        }
    }

    internal fun getTVShows(): LiveData<List<TV>?> {
        return listTv
    }

    internal fun insertFavorite(fav: Favorite) {
        repository.insert(fav)
    }

    internal fun deleteFavorite(fav: Favorite) {
        repository.delete(fav)
    }

    internal fun getAllFavorites(): LiveData<List<Favorite>> {
        return allFavorite
    }
}
