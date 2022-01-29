package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class CreateGroup2(
    val token: TokenDto,
    val group: Group
)

data class Group (

    val name : String,
    val description : String
)