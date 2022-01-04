package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

object HttpRequests {
    private var Response = ArrayList<FilterByGroupId>()
    private var ResponseFilterRating = ArrayList<FilterRating>()
    private var ResponseCreateGroup = ArrayList<CreateGroup>()

    fun getMovieById(url: String): ArrayList<FilterByGroupId> {
        url.httpGet().responseObject(FilterByGroupId.Deserializer()) { request, response, result ->
            val (item, err) = result

            item?.forEach { element ->
                Response.add(element)
            }
        }
        return Response
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

}