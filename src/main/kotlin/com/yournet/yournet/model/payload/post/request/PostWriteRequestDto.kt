package com.yournet.yournet.model.payload.post.request

import com.yournet.yournet.model.payload.posthashtag.request.PostHashTagRequestDto

data class PostWriteRequestDto(
    var title: String,
    var content: String,
    var image: String? = null,
    var hashTag: List<PostHashTagRequestDto>? = null
)