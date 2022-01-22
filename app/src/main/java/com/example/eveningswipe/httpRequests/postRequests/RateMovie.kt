package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto
import com.google.gson.annotations.SerializedName

data class RateMovie(
    @SerializedName("token") val token: TokenDto,
    @SerializedName("rating") val rating: Rating
)
data class Rating (

    @SerializedName("filterId") val filterId : Int,
    @SerializedName("movieId") val movieId : String
)