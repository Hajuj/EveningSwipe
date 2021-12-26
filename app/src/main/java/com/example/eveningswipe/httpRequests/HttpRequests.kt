package com.example.eveningswipe.httpRequests

import com.github.kittinunf.fuel.httpGet

object HttpRequests {
    private var Response = ArrayList<FilterByGroupId>()

    fun getMovieById(url: String): ArrayList<FilterByGroupId> {
        url.httpGet().responseObject(FilterByGroupId.Deserializer()) { request, response, result ->
            val (item, err) = result

            item?.forEach { element ->
                Response.add(element)
            }
        }
        return Response
    }
}