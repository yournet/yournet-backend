package com.yournet.yournet.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="post_like")
class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val likeId: Int,
    val userId: Int,
    val postId: Int
):BaseEntity() {
    constructor(userId: Int, postId: Int): this(0, userId, postId)
}