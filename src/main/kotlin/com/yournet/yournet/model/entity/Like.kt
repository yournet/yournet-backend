package com.yournet.yournet.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@Table(name="post_like")
class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val likeId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: User,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    var post: Post,
):BaseEntity() {
    constructor(user: User, post: Post):this(0,user,post)
}