package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Int> {
}