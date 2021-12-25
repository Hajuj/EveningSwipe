package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.Todos
import com.github.kittinunf.fuel.httpGet

object HttpRequests {
    private var Aufgaben = ArrayList<Todos>()

    fun getRequests(url: String): ArrayList<Todos> {
        url.httpGet().responseObject(Todos.Deserializer()) { request, response, result ->
            val (aufgaben, err) = result

            //Add to ArrayList
            aufgaben?.forEach { todo ->
                Aufgaben.add(todo)
            }

            println(Aufgaben)
        }

        return Aufgaben

    }
}