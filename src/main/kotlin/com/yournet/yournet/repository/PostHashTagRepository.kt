package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.PostHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface PostHashTagRepository: JpaRepository<PostHashTag, Int> {
}