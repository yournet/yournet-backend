package com.yournet.yournet.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UserScoreHashTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userScoreHashTagId: Int,
    val userId: Int,
    val hashTagId: Int,
    val score: Int
)