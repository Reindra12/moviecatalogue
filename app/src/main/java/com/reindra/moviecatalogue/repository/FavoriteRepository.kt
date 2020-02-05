package com.reindra.moviecatalogue.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.reindra.moviecatalogue.database.dao.FavoriteDao
import com.reindra.moviecatalogue.entity.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao, private val category: String) {

    private val allFavorites: LiveData<List<Favorite>> = favoriteDao.getByCategory(category)

    fun insert(fav: Favorite) {
        InsertFavoriteAsyncTask(
            favoriteDao
        ).execute(fav)
    }

    fun delete(fav: Favorite) {
        DeleteFavoriteAsyncTask(
            favoriteDao
        ).execute(fav)
    }

    fun getAllFavorites(): LiveData<List<Favorite>> {
        return allFavorites
    }

    fun getFavorite(id: String): LiveData<List<Favorite>> {
        val favorite: LiveData<List<Favorite>> = favoriteDao.getById(id, category)
        return favorite
    }

    private class InsertFavoriteAsyncTask(val favoriteDao: FavoriteDao) :
        AsyncTask<Favorite, Unit, Unit>() {

        override fun doInBackground(vararg favorite: Favorite) {
            favoriteDao.insert(favorite[0])
        }
    }

    private class DeleteFavoriteAsyncTask(val favoriteDao: FavoriteDao) :
        AsyncTask<Favorite, Unit, Unit>() {

        override fun doInBackground(vararg favorite: Favorite) {
            favoriteDao.delete(favorite[0])
        }
    }

}