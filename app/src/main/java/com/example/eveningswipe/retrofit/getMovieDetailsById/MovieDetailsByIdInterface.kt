package com.example.eveningswipe.retrofit.getMovieDetailsById

import retrofit2.Call
import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.http.Path


interface MovieDetailsByIdInterface {
    @GET("{id}")
    //fun getMovieResults(): Call<MovieResult>
    fun getMovieResults(@Path("id") id: String): Call<MovieResult>
}