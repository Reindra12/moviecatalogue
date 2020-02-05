package com.reindra.moviecatalogue.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.reindra.moviecatalogue.BuildConfig
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.repository.AppDatabase
import com.reindra.moviecatalogue.repository.FavoriteRepository
import com.reindra.moviecatalogue.util.CategoryEnum
import com.reindra.moviecatalogue.util.ResponseCodeEnum
import cz.msebera.android.httpclient.Header


class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val listMovies: MutableLiveData<List<MovieItems>> = MutableLiveData()
    private lateinit var allFavorite: LiveData<List<Favorite>>
    private var db: AppDatabase? = null
    private lateinit var repository: FavoriteRepository
    private val app = application

    internal fun onViewAttached() {
        db = AppDatabase.getAppDataBase(app)
        repository = FavoriteRepository(db!!.favoriteDao(), CategoryEnum.MOVIE.value)
        allFavorite = repository.getAllFavorites()

    }

    internal fun setMovies() {
        val client = AsyncHttpClient()
        val url =
            "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.MOVIE_API_KEY}&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray
            ) {
                if (statusCode == ResponseCodeEnum.OK.code) {
                    val movieResponse =
                        Gson().fromJson(String(responseBody), MovieResponse::class.java)
                    listMovies.postValue(movieResponse.results)
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
    }

    internal fun getMovies(): LiveData<List<MovieItems>?> {
        return listMovies
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












