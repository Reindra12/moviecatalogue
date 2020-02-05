package com.reindra.moviecatalogue.model

data class TVResponse (
    var page: Int? = 0,
    var results: List<TV>? = null
)