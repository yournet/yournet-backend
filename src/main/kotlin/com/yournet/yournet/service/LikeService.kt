package com.yournet.yournet.service

import com.yournet.yournet.model.entity.Like
import com.yournet.yournet.model.payload.like.request.LikeRequestDto
import com.yournet.yournet.model.payload.like.request.LikeResponseDto
import com.yournet.yournet.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val userService: UserService
    ) {

    fun createLike(body: LikeRequestDto ,jwt: String): LikeResponseDto {
        val userId = userService.getValidUser(jwt)
        if(userId!=null){
            val like = Like(
                userId = userId.userId,
                postId = body.postId
            )
            val savedLike = likeRepository.save(like)

            //TODO: 점수 평가 로직 추가
            //hashtag like 증가
            return LikeResponseDto(savedLike.likeId , savedLike.userId, savedLike.postId)
        }
        throw Exception("Invalid reqeust")
    }

    fun deleteLike(likeId: Int) {
        likeRepository.deleteById(likeId)
    }
}
