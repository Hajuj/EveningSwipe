package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.postRequests.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.coroutines.*
import kotlinx.coroutines.runBlocking

object HttpRequests {
    private var ResponseFilterByGroupId = ArrayList<FilterByGroupId>()
    private var ResponseFilterRating = ArrayList<FilterRating>()
    lateinit var responseUserInfo: UserInfoDto
    var responseToken: TokenDto? = null
    var responseGroupInfo: GroupInfoDto? = null
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
                    println(responseToken?.token)

                }
        return
    }

    /**
     * method to check whether token has already been initialized
     */
   /* fun checkifInitialized() : Boolean{
        println("Token here: " + responseToken)
        val token = responseToken.toString()
        return this::responseToken.isInitialized
    }*/

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
                 err?. let{ println("ERROR !!")}
                println("reg: "+ req + " res: " + res+ " result: " + result)
                 println(responseUserInfo.userName)
        }
    }



    fun getGroupInformation(url: String, token: TokenDto, groupId: Int, name: String) {
        val groupInfo = GroupInfoDto(
                name = name,
                token = token,
                groupid = groupId
        )
        url.httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(groupInfo).toString())
                .response()
                { req, res, result ->

                    val (info, err) = result
                    //info?.let { responseGroupInfo = info }
                    err?. let{ println("ERROR !!")}
                    println("reg: "+ req + " res: " + res+ " result: " + result)
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