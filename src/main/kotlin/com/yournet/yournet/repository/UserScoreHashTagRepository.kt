package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.HashTag
import com.yournet.yournet.model.entity.User
import com.yournet.yournet.model.entity.UserScoreHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface UserScoreHashTagRepository: JpaRepository<UserScoreHashTag, Int> {
    fun findByUserAndHashTag(user: User, hashTag: HashTag): UserScoreHashTag?
}