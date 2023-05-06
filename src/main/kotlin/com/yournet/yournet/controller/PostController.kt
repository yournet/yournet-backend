package com.yournet.yournet.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(

) {

    //게시글 작성 api
    @PostMapping("post")
    fun writePost() {

    }
}