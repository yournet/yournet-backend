package com.yournet.yournet.model.payload.like.request

data class LikeResponseDto(
    val id: Int,
    val userId: Int,
    val postId: Int
)