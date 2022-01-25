package com.example.eveningswipe.httpRequests

import android.content.ClipData
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

class GetFilterRating: ArrayList<GetFilterRating2>(

){
    class Deserializer : ResponseDeserializable<GetFilterRating> {
        override fun deserialize(content: String) = Gson().fromJson(content, GetFilterRating::class.java)
    }
}

data class GetFilterRating2 (

    val id: Int,
    val movie_id: String,
    val filter_id: String,
    val rating: String,
) {
    class Deserializer : ResponseDeserializable<Array<GetFilterRating2>> {
        override fun deserialize(content: String): Array<GetFilterRating2>? =
            Gson().fromJson(content, Array<GetFilterRating2>::class.java)
    }
}
