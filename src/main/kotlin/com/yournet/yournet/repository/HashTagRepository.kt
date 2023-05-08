package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.HashTag
import org.springframework.data.jpa.repository.JpaRepository

interface HashTagRepository: JpaRepository<HashTag, Int> {
    fun findByHashTagName(hashTagName:String): HashTag?
}