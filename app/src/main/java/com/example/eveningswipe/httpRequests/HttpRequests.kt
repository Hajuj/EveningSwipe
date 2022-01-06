package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

object HttpRequests {
    private var ResponseFilterByGroupId = ArrayList<FilterByGroupId>()
    private var ResponseFilterRating = ArrayList<FilterRating>()
    private var ResponseCreateGroup = ArrayList<CreateGroup>()
    private var ResponseMovieResult = ArrayList<MovieDetailsById>()

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
                println("!!!!!"+element)
            }
        }
        return ResponseMovieResult
    }*/

    fun postRateMovie(url: String){
        url.httpPost().response{ request, response, result -> }
    }
}