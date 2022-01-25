package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.postRequests.*
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import java.util.logging.Level.parse

object HttpRequests {
    var responseFilterByGroupId: GetFilterByGroupId2? = null
    lateinit var responseUserInfo: UserInfoDto
    var responseToken: TokenDto? = null
    var responseGroupInfo: PostGroupInfo? = null
    var responseFindUserInfo: FindUserDto? = null
    var responseMovieDetails: GetMovieDetails? = null
    var responseFilterRating: Array<GetFilterRating2>? = null
    private var ResponseCreateGroup = ArrayList<CreateGroup>()
    var groupName: String? = null
    var wrongLoginData: String? = ""

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

    fun postLoginUser(url: String, email: String, password: String): Boolean? {
        val loginUser = LoginUser(
            email = email,
            password = password
        )
        var success: Boolean? = null

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(loginUser).toString())
            .responseObject(TokenDto.Deserializer())
            { req, res, result ->
                val (info, err) = result
                info?.let { responseToken = info }
                err?.let {
                    wrongLoginData = "Your login data are wrong. Please try again!"
                    println("wrongLoginDataHTTP "+wrongLoginData) }
                println("reg: " + req + " res: " + res + " result: " + result + "code? " + res.statusCode)
                println(responseToken?.token)
                if(res.statusCode == 400){
                    success = false
                }else{
                    success = true
                }
            }.join()

        return success
    }

    /**
     * method to check whether token has already been initialized
     */
    fun checkifInitialized() : Boolean{
        return this::responseUserInfo.isInitialized
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
                println("reg: " + req + " res: " + res + " result: " + result)
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
                err?.let { println("ERROR !!") }
                println("reg: " + req + " res: " + res + " result: " + result)
                println("user name: " + responseUserInfo.userName)
            }
    }

    fun getGroupInformation(url: String, token: TokenDto, groupId: Int) {
        val groupInfo = GroupInfoDto(
                //name = name,
                token = token,
                groupid = groupId
        )
        url.httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(groupInfo).toString())
                .responseObject(PostGroupInfo.Deserializer())
                { req, res, result ->

                    val (info, err) = result
                    info?.let { responseGroupInfo = info }
                    err?. let{ println("ERROR !!")}
                    println("reg: "+ req + " res: " + res+ " result: " + result)
                    responseGroupInfo = PostGroupInfo(result.get().filter, result.get().member , result.get().name)
                    groupName =  result.get().name
                }
    }

    fun getAllUser(url: String, token: String, search: String): Boolean? {
        val findUser = FindUserInfoDto(
            token = token,
            search = search
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(findUser).toString())
            .responseObject(FindUserDto.Deserializer())
            { req, res, result ->
                if(res.statusCode == 400){
                    success = false
                }else{
                    success = true
                }

                val (info, err) = result
                info?.let { responseFindUserInfo = info }
                err?. let{ println("ERROR !!")}
                println("reg: "+ req + " res: " + res+ " result: " + result)
                responseFindUserInfo = FindUserDto(result.get().email, result.get().name)
            }.join()
        return success
    }

    fun postCreatedGroup2(url: String,tok: String, nam: String, descript: String): Boolean?{
        val createGroup = CreateGroup2(
            token = TokenCG(token = tok),
            group = Group(name = nam, description = descript)
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(createGroup).toString())
            .responseObject(PostGroupInfo.Deserializer())
            { req, res, result ->

                if(res.statusCode == 400){
                    success = false
                }else{
                    success = true
                }

                val (info, err) = result
                info?.let { responseGroupInfo = info }
                err?.let { println("ERROR !!") }
                println("reg: " + req + " res: " + res + " result: " + result)
                responseGroupInfo =
                    PostGroupInfo(result.get().filter, result.get().member, result.get().name)
            }.join()
        return success
    }


    fun getFilterByGroupId(url: String, token: TokenDto, groupId: Int) {
        val movieInfo = PostFilterByGroupId(
            token = token,
            groupId = groupId
        )
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(movieInfo).toString())
            .responseObject(GetFilterByGroupId2.Deserializer())
            { req, res, result ->

                val (info, err) = result
                //info?.let { responseFilterByGroupId = info }
                err?.let { println("ERROR in getFilterByGroupId !!" + err) }
                println("no error : reg: " + req + " res: " + res + " result: " + result)
                responseFilterByGroupId = GetFilterByGroupId2(
                    result.get()[0].id,
                    result.get()[0].genre_1 ,
                    result.get()[0].genre_2,
                    result.get()[0].genre_3,
                    result.get()[0].min_year,
                    result.get()[0].max_year,
                    result.get()[0].rating,
                    result.get()[0].votes,
                    result.get()[0].max_runtime,
                    result.get()[0].size,
                    result.get()[0].group_id,
                    result.get()[0].selection)
            }
        }

        fun getMovieDetails(url: String, token: TokenDto, movieId: String): Boolean? {
            val movieInfo = PostMovieDetailsById(
                token = token,
                movieId = movieId
            )
            var success: Boolean? = null
            url.httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(movieInfo).toString())
                .responseObject(GetMovieDetails.Deserializer())
                { req, res, result ->

                    val (info, err) = result
                    info?.let { responseMovieDetails = info }
                    err?.let { println("ERROR !!") }
                    println("reg: " + req + " res: " + res + " result: " + result)
                    /*responseMovieDetails = GetMovieDetails(
                        result.get().original_title,
                        result.get().overview,
                        result.get().popularity,
                        result.get().poster_path,
                        result.get().release_date,
                        result.get().title,
                        result.get().vote_average,
                        result.get().vote_count
                    )*/
                    if(res.statusCode == 400){
                        success = false
                    }else{
                        success = true
                    }
                }.join()
            return success
        }

        fun postRateMovie(url: String, movId: String, filId: Int, tok: TokenDto) {
            val rateMovie = RateMovie(
                token = tok,
                rating = Rating(filterId = filId, movieId = movId)
            )

            url.httpPost()
                .header("Content-Type" to "application/json")
                .body(Gson().toJson(rateMovie).toString())
                .response() { req, res, result ->
                    val (info, err) = result
                    err?.let { println("ERROR !!") }
                    println("rated!!" + "reg: " + req + " res: " + res + " result: " + result)
                }
        }

    fun getFilterRating(url: String, token: TokenDto, filterId: Int): Boolean? {
        val filterRating = PostFilterRating(
            token = token,
            filterId = filterId
        )
        var success: Boolean? = null

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(filterRating).toString())
            .responseObject(GetFilterRating2.Deserializer())
            { req, res, result ->

                val (info, err) = result
                info?.let { responseFilterRating = info }
                err?.let { println("ERROR !!"+ err) }
                println("reg: " + req + " res: " + res + " result: " + result)

                if(res.statusCode == 400){
                    success = false
                }else{
                    success = true
                }
            }.join()

        return success
    }
}
