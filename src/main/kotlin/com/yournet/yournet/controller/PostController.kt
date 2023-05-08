package com.yournet.yournet.controller

import com.yournet.yournet.model.payload.post.request.PostWriteRequestDto
import com.yournet.yournet.model.payload.post.response.PostResponseDto
import com.yournet.yournet.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val postService: PostService
) {

    //게시글 작성 api
    @PostMapping("post")
    fun writePost(
        @RequestBody body: PostWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ):ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.savePost(body, jwt))
    }

    //특정 게시글 가져오기 api 추천 api 적용을 어떻게할지 고민해야함
    @GetMapping("post/{postId}")
    fun getPost(
        @RequestParam("postId") postId:Int,
    ):ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.getPost(postId))
    }

    //게시글들 불러오기 api
    @GetMapping("posts")
    fun getPosts(
        @RequestParam("page") page:Int,
        @RequestParam("size") size:Int,
        @RequestParam(required = false, defaultValue = "createdAt") sort:String?,
        @RequestParam(required = false) hashTag:String?,
    ):ResponseEntity<List<PostResponseDto>> {
        return ResponseEntity.ok(postService.getPostsList(page, size, sort, hashTag))
    }

    @PatchMapping("post/{postId}")
    fun updatePost(
        @RequestParam("postId") postId:Int,
        @RequestBody body: PostWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ):ResponseEntity<PostResponseDto> {
        return ResponseEntity.ok(postService.updatePost(postId, body, jwt))
    }

    @DeleteMapping("post/{postId}")
    fun deletePost(
        @RequestParam("postId") postId:Int,
        @RequestHeader("Authorization") jwt: String
    ):ResponseEntity<String> {
        postService.deletePost(postId, jwt)
        return ResponseEntity.ok("delete success")
    }


}