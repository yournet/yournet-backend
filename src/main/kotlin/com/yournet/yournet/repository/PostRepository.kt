package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface PostRepository: JpaRepository<Post, Int> {
    @Query("SELECT p FROM Post p JOIN p.postHashtag ph JOIN ph.hashTag h WHERE h.hashTagName = :hashTagName")
    fun findAllByHashTagName(page: Pageable, hashTagName:String): Page<Post>
}