package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.postRequests.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import kotlin.reflect.typeOf

object HttpRequests {
    private var ResponseFilterByGroupId = ArrayList<FilterByGroupId>()
    private var ResponseFilterRating = ArrayList<FilterRating>()
    lateinit  private var responseUserInfo: UserInfoDto
    private var ResponseCreateGroup = ArrayList<CreateGroup>()
    private var ResponseMovieResult = ArrayList<MovieDetailsById>()

    fun postRegisterUser(url: String, nam: String, email: String, password: String) {
        val registerUser = RegisterUser(
                name = nam,
                email = email,
                password = password
        )

        url.httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(registerUser).toString())
                .response() { req, res, result ->

                }
    }

    fun postLoginUser(url: String, email: String, password: String) {
        val loginUser = LoginUser(
                email = email,
                password = password
        )

        url.httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(loginUser).toString())
                .response() { req, res, result ->

                }
    }

    fun postAddUserToGroup(url: String, tok: String, groupId: Int, userToAdd: String) {
        val addUserToGroup = AddUserToGroup(
            token = TokenAddUser(token = tok),
            add = Add(groupId = groupId, toAdd = userToAdd)
        )

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(addUserToGroup).toString())
            .response() { req, res, result ->

            }
    }

    fun getUserInformation(url: String, token: TokenDto): UserInfoDto {
        val url2 = "http://10.0.2.2:8080/api/user"
        val token2 = TokenDto("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI0MjIiLCJpYXQiOjE2NDI1MDAzMDIsImV4cCI6MTY0MjU4NjcwMn0.9Coew80TwhOZ_9_q3jcb1of_WIxdO0BR-N8RgPkpAog")
        url2.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(token2).toString())
            .responseObject(UserInfoDto.Deserializer())
            { req, res, result ->

            val (info, err) = result
                info?.let { responseUserInfo = info }


            /*item?.forEach { element ->
                ResponseUserInfo.add(element)
            }*/

        }

        return responseUserInfo
    }


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

    fun postCreatedGroup2(url: String,tok: String, nam: String, descript: String) {
        val createGroup = CreateGroup2(
            token = TokenCG(token = tok),
            group = Group(name = nam, description = descript)
        )

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(createGroup).toString())
            .response() { req, res, result ->

            }
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
}