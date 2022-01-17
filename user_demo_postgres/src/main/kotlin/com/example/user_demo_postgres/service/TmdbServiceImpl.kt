package com.example.user_demo_postgres.service

import com.example.user_demo_postgres.entity.TmdbMovieInfo
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class TmdbServiceImpl : TmdbService {
    override fun getMovieResource(id: String): TmdbMovieInfo? {
        val client: WebClient = WebClient.create()
        val responseSpec = client.get()
            .uri("https://api.themoviedb.org/3/find/$id?api_key=780d1a98f36880546fec917c8365444e&language=en-US&external_source=imdb_id")
            .retrieve()
            .bodyToMono(TmdbMovieInfo::class.java )
            .block()

        print(responseSpec)



        return responseSpec


    }


}