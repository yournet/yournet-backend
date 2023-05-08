package com.yournet.yournet.model.payload.post.response

import com.yournet.yournet.model.payload.auth.response.UserResponseDto
import com.yournet.yournet.model.payload.posthashtag.response.PostHashTagResponseDto

data class PostResponseDto(
    var id: Int,
    var title: String,
    var content: String,
    var image: String? = null,
    var hashTag: List<PostHashTagResponseDto>? = null,
    var user : UserResponseDto
)