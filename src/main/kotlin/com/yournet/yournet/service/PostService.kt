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
        postRepository.save(savedPost)

        return PostResponseDto(
            id = savedPost.postId,
            title = savedPost.title,
            content = savedPost.content,
            image = savedPost.postImage,
            hashTag = hashTagResponseDtoList,
            user = userResponse
        )
    }

    fun getPost(postId:Int): PostResponseDto {
        //TODO: findPost가 null일경우 예외처리를 해줘야함
        val findPost = postRepository.findById(postId).orElse(null)

        val hashTagResponseDtoList = mutableListOf<PostHashTagResponseDto>()
        findPost?.postHashtag?.forEach{postHashTag ->
            hashTagResponseDtoList.add(
                PostHashTagResponseDto(
                    id = postHashTag.hashTag.hashTagId,
                    name = postHashTag.hashTag.hashTagName
                )
            )
        }
        val userResponse = UserResponseDto(
            userId = findPost?.user?.userId,
            name = findPost?.user?.name,
            email = findPost?.user?.email,
            createdAt = findPost?.user?.createdAt,
            updatedAt = findPost?.user?.updatedAt
        )
        return PostResponseDto(
            id = findPost.postId,
            title = findPost.title,
            content = findPost.content,
            image = findPost?.postImage,
            hashTag = hashTagResponseDtoList,
            user = userResponse
        )
    }

    fun getPostsList(page:Int, size:Int): List<PostResponseDto> {
        val findPosts = postRepository.findAll()
        val postsList = mutableListOf<PostResponseDto>()
        findPosts.forEach{post ->
            val hashTagResponseDtoList = mutableListOf<PostHashTagResponseDto>()
            post.postHashtag?.forEach{postHashTag ->
                hashTagResponseDtoList.add(
                    PostHashTagResponseDto(
                        id = postHashTag.hashTag.hashTagId,
                        name = postHashTag.hashTag.hashTagName
                    )
                )
            }
            val userResponse = UserResponseDto(
                userId = post.user?.userId,
                name = post.user?.name,
                email = post.user?.email,
                createdAt = post.user?.createdAt,
                updatedAt = post.user?.updatedAt
            )
            postsList.add(
                PostResponseDto(
                    id = post.postId,
                    title = post.title,
                    content = post.content,
                    image = post.postImage,
                    hashTag = hashTagResponseDtoList,
                    user = userResponse
                )
            )
        }
        return postsList
    }
}