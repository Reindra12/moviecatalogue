package com.reindra.moviecatalogue.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.reindra.moviecatalogue.entity.Favorite
import com.reindra.moviecatalogue.repository.AppDatabase
import com.reindra.moviecatalogue.repository.FavoriteRepository

class DetailModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase? = null
    private lateinit var repository: FavoriteRepository
    private lateinit var favorite: LiveData<List<Favorite>>
    private val app = application

    internal fun onViewAttached(movieId: String, category: String) {
        db = AppDatabase.getAppDataBase(app)
        repository = FavoriteRepository(db!!.favoriteDao(), category)
        favorite = repository.getFavorite(movieId)
    }

    internal fun insertFavorite(fav: Favorite) {
        repository.insert(fav)
    }

    internal fun deleteFavorite(fav: Favorite) {
        repository.delete(fav)
    }

    internal fun getFavorite(): LiveData<List<Favorite>> {
        return favorite
    }
}