package com.example.eveningswipe.httpRequests

import com.example.eveningswipe.httpRequests.postRequests.*
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson

object HttpRequests {
    var responseFilterByGroupId: Array<GetFilterByGroupId2>? = null
    lateinit var responseUserInfo: UserInfoDto
    var responseToken: TokenDto? = null
    var responseGroupInfo: PostGroupInfo? = null
    var responseFindUserInfo: FindUserDto? = null
    var responseMovieDetails: GetMovieDetails? = null
    var responseOtherMovieDetails: GetOtherMovieDetails? = null
    var responseFilterRating: Array<GetFilterRating2>? = null
    var responseSwipeState: GetSwipeState? = null
    var groupName: String? = null
    var wrongLoginData: String? = ""

    /**
     * function to send request to register a new user
     */
    fun postRegisterUser(url: String, nam: String, email: String, password: String): Boolean? {
        val registerUser = RegisterUser(
            name = nam,
            email = email,
            password = password
        )
        var success: Boolean? = null

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(registerUser).toString())
            .response() { req, res, result ->
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to login user
     */
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
                }
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()

        return success
    }

    /**
     * method to check whether token has already been initialized
     */
    fun checkifInitialized(): Boolean {
        return this::responseUserInfo.isInitialized
    }

    /**
     * function to send request to add user to group
     */
    fun postAddUserToGroup(url: String, tok: String, groupId: Int, userToAdd: String): Boolean? {
        val addUserToGroup = AddUserToGroup(
            token = TokenAddUser(token = tok),
            add = Add(groupId = groupId, toAdd = userToAdd)
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(addUserToGroup).toString())
            .response() { req, res, result ->
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to get information about user
     */
    fun getUserInformation(url: String, token: TokenDto): Boolean? {
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(token).toString())
            .responseObject(UserInfoDto.Deserializer())
            { req, res, result ->

                val (info, err) = result
                info?.let { responseUserInfo = info }
                err?.let { println("ERROR !!") }
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to get information about the group
     */
    fun getGroupInformation(url: String, token: TokenDto, groupId: Int): Boolean? {
        val groupInfo = GroupInfoDto(
            token = token,
            groupid = groupId
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(groupInfo).toString())
            .responseObject(PostGroupInfo.Deserializer())
            { req, res, result ->

                val (info, err) = result
                info?.let { responseGroupInfo = info }
                err?.let { println("ERROR !!") }
                responseGroupInfo =
                    PostGroupInfo(result.get().filter, result.get().member, result.get().name)
                groupName = result.get().name
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to get all user
     */
    fun getAllUser(url: String, token: String, email: String): Boolean? {
        val findUser = FindUserInfoDto(
            token = token,
            email = email
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(findUser).toString())
            .responseObject(FindUserDto.Deserializer())
            { req, res, result ->
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }

                val (info, err) = result
                info?.let { responseFindUserInfo = info }
                err?.let { println("ERROR !!") }
                responseFindUserInfo = FindUserDto(result.get().email, result.get().name)
            }.join()
        return success
    }

    /**
     * function to send request to create a new group
     */
    fun postCreatedGroup2(url: String, tok: String, nam: String, descript: String): Boolean? {
        val createGroup = CreateGroup2(
            token = TokenDto(token = tok),
            group = Group(name = nam, description = descript)
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(createGroup).toString())
            .responseObject(PostGroupInfo.Deserializer())
            { req, res, result ->

                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }

                val (info, err) = result
                info?.let { responseGroupInfo = info }
                err?.let { println("ERROR !!") }
                responseGroupInfo =
                    PostGroupInfo(result.get().filter, result.get().member, result.get().name)
            }.join()
        return success
    }

    /**
     * function to send request to get the filter from the group
     */
    fun getFilterByGroupId(url: String, token: TokenDto, groupId: Int): Boolean? {
        val movieInfo = PostFilterByGroupId(
            token = token,
            groupId = groupId
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(movieInfo).toString())
            .responseObject(GetFilterByGroupId2.Deserializer())
            { req, res, result ->

                val (info, err) = result
                info?.let { responseFilterByGroupId = info }
                err?.let { println("ERROR in getFilterByGroupId !!" + err) }
                if (res.statusCode == 400 || responseFilterByGroupId.contentEquals(emptyArray<GetFilterByGroupId2>())) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to get the details from movie
     */
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
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to get the details from movie - other database
     */
    fun getOtherMovieDetails(url: String, token: TokenDto, movieId: String): Boolean? {
        val movieInfo = PostMovieDetailsById(
            token = token,
            movieId = movieId
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(movieInfo).toString())
            .responseObject(GetOtherMovieDetails.Deserializer())
            { req, res, result ->

                val (info, err) = result
                info?.let { responseOtherMovieDetails = info }
                err?.let { println("ERROR !!") }
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send request to rate a movie (like)
     */
    fun postRateMovie(url: String, movId: String, filId: Int, like: Boolean, tok: TokenDto) {
        val rateMovie = RateMovie(
            token = tok,
            rating = Rating(filterId = filId, movieId = movId, like = like)
        )

        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(rateMovie).toString())
            .response() { req, res, result ->
                val (info, err) = result
                err?.let { println("ERROR !!") }
            }
    }

    /**
     * function so send a request to get the ranking of all movies in a filter
     */
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
                err?.let { println("ERROR !!" + err) }

                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()

        return success
    }

    /**
     * function to send request to create a new filter
     */
    fun postCreateFilter(url: String, token: TokenDto, filter: FilterDto): Boolean? {
        val createFilter = CreateFilter(
            token = token,
            filter = filter
        )

        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(createFilter).toString())
            .response() { req, res, result ->
                val (info, err) = result
                err?.let { println("ERROR !!" + err) }
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }
            }.join()
        return success
    }

    /**
     * function to send a request to get the current swipe state
     */
    fun getSwipeState(url: String, token: TokenDto, filterId: Int): Boolean? {
        val swipeState = PostSwipeState(
            token = token,
            filterId = filterId
        )
        var success: Boolean? = null
        url.httpPost()
            .header("Content-Type" to "application/json")
            .body(Gson().toJson(swipeState).toString())
            .responseObject(GetSwipeState.Deserializer())
            { req, res, result ->
                if (res.statusCode == 400) {
                    success = false
                } else {
                    success = true
                }

                val (info, err) = result
                info?.let { responseSwipeState = info }
                err?.let { println("ERROR !!" + err) }
            }.join()
        return success
    }
}
