package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.rateMovie.RateMovie
import com.example.eveningswipe.httpRequests.rateMovie.Rating
import com.example.eveningswipe.httpRequests.rateMovie.Token
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson

object HttpRequests {
    private var ResponseFilterByGroupId = ArrayList<FilterByGroupId>()
    private var ResponseFilterRating = ArrayList<FilterRating>()
    private var ResponseCreateGroup = ArrayList<CreateGroup>()
    private var ResponseMovieResult = ArrayList<MovieDetailsById>()
    private var PostRateMovie = ArrayList<Rating>()

    fun getMovieById(url: String): ArrayList<FilterByGroupId> {
        url.httpGet().responseObject(FilterByGroupId.Deserializer()) { request, response, result ->
            val (item, err) = result

            item?.forEach { element ->
                ResponseFilterByGroupId.add(element)
            }
        }
        return ResponseFilterByGroupId
    }

    fun getFilterRating(url: String): ArrayList<FilterRating> {
        url.httpGet().responseObject(FilterRating.Deserializer()) { request, response, result ->
            val (item, err) = result

            item?.forEach { element ->
                ResponseFilterRating.add(element)
            }
        }
        return ResponseFilterRating
    }

    fun postCreatedGroup(url: String): ArrayList<CreateGroup> {
        url.httpPost().responseObject(CreateGroup.Deserializer()) { request, response, result ->
            val (item, err) = result

            item?.forEach { element ->
                ResponseCreateGroup.add(element)
            }
        }
        return ResponseCreateGroup
    }
    /*
    fun getMovieResult(url: String): ArrayList<MovieDetailsById>{
        url.httpGet().responseObject(MovieDetailsById.Deserializer()) { request, response, result ->
        val (item, err) = result

            item?.forEach { element ->
                ResponseMovieResult.add(element)
            }
        }
        return ResponseMovieResult
    }*/

    fun postRateMovie(url: String, movId: String, filId: Int, tok: String) {
        val rateMovie = RateMovie(
            token = Token(token = tok),
            rating = Rating(filterId = filId, movieId = movId)
        )

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(rateMovie).toString())
            .response() { req, res, result ->

            }
    }
        /*url.httpPost().responseObject(RateMovie.Deserializer()) { request, response, result ->
            val (item, err) = result

            item?.forEach { element ->
                PostRateMovie.add(element)
            }
            PostRateMovie[0].rating.filterId = filterId
            PostRateMovie[0].rating.movieId = movieId
        }
    }*/
        /*   val post = Rating(filterId, movieId)

        val (request, response, result)
                = Fuel.post(url)
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(post).toString())
    }*/

}