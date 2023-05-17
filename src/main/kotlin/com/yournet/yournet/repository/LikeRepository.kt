package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository: JpaRepository<Like, Int> {
}