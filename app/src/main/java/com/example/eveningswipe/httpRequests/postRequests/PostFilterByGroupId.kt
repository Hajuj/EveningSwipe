package com.example.eveningswipe.httpRequests.postRequests

import com.example.eveningswipe.httpRequests.TokenDto

data class PostFilterByGroupId(
    val token: TokenDto,
    val groupId: Int
)