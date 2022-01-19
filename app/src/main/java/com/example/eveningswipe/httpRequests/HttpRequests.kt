package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.postRequests.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import kotlin.reflect.typeOf

object HttpRequests {
    private var ResponseFilterByGroupId = ArrayList<FilterByGroupId>()
    private var ResponseFilterRating = ArrayList<FilterRating>()
    lateinit var responseUserInfo: UserInfoDto
    lateinit var responseToken: TokenDto
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
                .responseObject(TokenDto.Deserializer())
                { req, res, result ->

                    val (info, err) = result
                    info?.let { responseToken = info }
                    println("reg: "+ req + " res: " + res+ " result: " + result)
                    println(responseToken.token)

                }
    }

    /**
     * method to check whether token has already been initialized
     */
    fun checkifInitialized() : Boolean{
        return this::responseToken.isInitialized
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

    fun getUserInformation(url: String, token: TokenDto) {
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(token).toString())
            .responseObject(UserInfoDto.Deserializer())
             { req, res, result ->

            val (info, err) = result
                info?.let { responseUserInfo = info }
                println("reg: "+ req + " res: " + res+ " result: " + result)
                 println(responseUserInfo.userName)
        }
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