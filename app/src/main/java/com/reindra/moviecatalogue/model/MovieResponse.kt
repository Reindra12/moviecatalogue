package com.reindra.moviecatalogue.model

data class MovieResponse (
    var page: Int? = 0,
    var results: List<MovieItems>? = null
)