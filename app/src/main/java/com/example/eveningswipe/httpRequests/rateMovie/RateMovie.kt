package com.example.eveningswipe.httpRequests.rateMovie

import com.google.gson.annotations.SerializedName

data class RateMovie(
    @SerializedName("token") val token: Token,
    @SerializedName("rating") val rating: Rating
)
data class Token(
    @SerializedName("token") val token : String
)
data class Rating (

    @SerializedName("filterId") val filterId : Int,
    @SerializedName("movieId") val movieId : String
)