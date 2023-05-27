package com.yournet.yournet.service

import com.yournet.yournet.model.entity.Like
import com.yournet.yournet.model.payload.like.request.LikeRequestDto
import com.yournet.yournet.model.payload.like.request.LikeResponseDto
import com.yournet.yournet.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val userService: UserService,
    private val postService: PostService,
    private val userScoreHashTagService: UserScoreHashTagService
) {

    fun createLike(body: LikeRequestDto, jwt: String): LikeResponseDto {
        val user = userService.getValidUser(jwt)
        val post = postService.getValidPost(body.postId)
        if (user != null) {
            val like = Like(
                user = user,
                post = post
            )
            val savedLike = likeRepository.save(like)

            //TODO: 점수 평가 로직 추가
            //hashtag like 증가
            val findPost = postService.getValidPost(body.postId)
            findPost.postHashtag?.forEach { hashtag ->
                userScoreHashTagService.updateScoreOnLike(jwt, hashtag.hashTag.hashTagName)
            }
            return LikeResponseDto(savedLike.likeId, savedLike.user.userId, savedLike.post.postId)
        }
        throw Exception("Invalid reqeust")
    }

    fun deleteLike(likeId: Int) {
        likeRepository.deleteById(likeId)
    }
}
