package com.yournet.yournet.repository

import com.yournet.yournet.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email:String): User?
}