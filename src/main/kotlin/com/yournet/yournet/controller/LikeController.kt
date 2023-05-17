package com.yournet.yournet.controller

import com.yournet.yournet.model.payload.like.request.LikeRequestDto
import com.yournet.yournet.model.payload.like.request.LikeResponseDto
import com.yournet.yournet.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/likes")
class LikeController(private val likeService: LikeService) {

    @PostMapping
    fun createLike(
        @RequestBody request: LikeRequestDto,
        @RequestHeader("Authorization") jwt: String,
    ): ResponseEntity<LikeResponseDto> {
        val likeResponse = likeService.createLike(request,jwt)
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse)
    }
    //TODO: 좋아요 취소
    /*
    @DeleteMapping("/{likeId}")
    fun deleteLike(
        @PathVariable likeId: Int): ResponseEntity<Unit> {
        likeService.deleteLike(likeId)
        return ResponseEntity.noContent().build()
    }

     */
}
