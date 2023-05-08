package com.yournet.yournet.service

import com.yournet.yournet.model.entity.HashTag
import com.yournet.yournet.model.entity.Post
import com.yournet.yournet.model.entity.PostHashTag
import com.yournet.yournet.model.payload.posthashtag.request.PostHashTagRequestDto
import com.yournet.yournet.repository.HashTagRepository
import com.yournet.yournet.repository.PostHashTagRepository
import org.springframework.stereotype.Service

@Service
class PostHashTagService(
    private val postHashTagRepository: PostHashTagRepository,
    private val hashTagRepository: HashTagRepository,
) {
    fun savePostHashTags(
        bodyPostHashTags:List<PostHashTagRequestDto>?,
        post:Post
    ): MutableList<PostHashTag>? {
        if(bodyPostHashTags != null){
            val postHashTagList = mutableListOf<PostHashTag>()

            bodyPostHashTags.forEach{postHashTag ->
                val findHashTag = hashTagRepository.findByHashTagName(postHashTag.name)
                //해시태그가 이미 존재하면 포스트 해시태그 테이블에 저장
                if(findHashTag != null) {
                    val newPostHashTag =PostHashTag(
                        post = post,
                        hashTag = findHashTag
                    )
                    postHashTagList.add(newPostHashTag)

                }else{ //해시태그가 존재하지 않으면 해시태그,포스트해시태그 테이블에 저장
                    val newHashTag = hashTagRepository.save(
                        HashTag(
                            hashTagName = postHashTag.name
                        )
                    )
                    val newPostHashTag = PostHashTag(
                        post = post,
                        hashTag = newHashTag
                    )
                    postHashTagList.add(newPostHashTag)
                }
            }
            return postHashTagList
        }
        return null
    }
}