package com.yournet.yournet.service

import com.yournet.yournet.model.entity.Post
import com.yournet.yournet.model.payload.auth.response.UserResponseDto
import com.yournet.yournet.model.payload.post.request.PostWriteRequestDto
import com.yournet.yournet.model.payload.post.response.PostResponseDto
import com.yournet.yournet.model.payload.posthashtag.response.PostHashTagResponseDto
import com.yournet.yournet.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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

    fun getPostsList(page:Int, size:Int, sort:String?,hashtag:String?): List<PostResponseDto> {
        val pageRequest: Pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, sort)
        val posts: Page<Post>
        if(hashtag == null) {
            posts = postRepository.findAll(pageRequest)
        }else{
            posts = postRepository.findAllByHashTagName(pageRequest, hashtag)

        }
        val postResponseDtoList = mutableListOf<PostResponseDto>()
        posts.forEach{post ->
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
            postResponseDtoList.add(
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
        return postResponseDtoList
    }

    fun getValidPost(postId:Int): Post {
        val findPost = postRepository.findById(postId).orElse(null)
        return findPost ?: throw Exception("존재하지 않는 게시글입니다.")
    }

    fun updatePost(post: Post,jwt: String){
        val findUser = userService.getValidUser(jwt)
        if(post.user?.userId != findUser?.userId){
            throw Exception("게시글 작성자가 아닙니다.")
        }
        postRepository.save(post)

        //TODO: 저장하고 response 추가하기
    }
}