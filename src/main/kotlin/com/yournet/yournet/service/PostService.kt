package com.yournet.yournet.service

import com.yournet.yournet.model.entity.Post
import com.yournet.yournet.model.payload.auth.response.UserResponseDto
import com.yournet.yournet.model.payload.post.request.PostWriteRequestDto
import com.yournet.yournet.model.payload.post.response.PostResponseDto
import com.yournet.yournet.model.payload.posthashtag.response.PostHashTagResponseDto
import com.yournet.yournet.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService,
    private val postHashTagService: PostHashTagService,
) {
    fun savePost(
        body:PostWriteRequestDto,
        jwt:String
    ): PostResponseDto {
        val findUser = userService.getValidUser(jwt)
        val post = Post(
            title = body.title,
            content = body.content,
            postImage = body.image,
            user = findUser
        )

        val savedPost = postRepository.save(post)

        //해시태그 저장 로직 수행
        val postHashTags = postHashTagService.savePostHashTags(body.hashTag, savedPost)
        savedPost.postHashtag = postHashTags

        val hashTagResponseDtoList = mutableListOf<PostHashTagResponseDto>()
        postHashTags?.forEach{postHashTag ->
            hashTagResponseDtoList.add(
                PostHashTagResponseDto(
                    id = postHashTag.hashTag.hashTagId,
                    name = postHashTag.hashTag.hashTagName
                )
            )
        }
        val userResponse = UserResponseDto(
            userId = findUser?.userId,
            name = findUser?.name,
            email = findUser?.email,
            createdAt = findUser?.createdAt,
            updatedAt = findUser?.updatedAt
        )

        return PostResponseDto(
            id = savedPost.postId,
            title = savedPost.title,
            content = savedPost.content,
            image = savedPost.postImage,
            hashTag = hashTagResponseDtoList,
            user = userResponse
        )
    }
}