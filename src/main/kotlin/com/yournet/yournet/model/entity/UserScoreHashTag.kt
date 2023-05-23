package com.yournet.yournet.model.entity

import javax.persistence.*

@Entity
data class UserScoreHashTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userScoreHashTagId: Int,

    @ManyToOne
    @JoinColumn(name = "userId")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "hashTagId")
    val hashTag: HashTag,

    var score: Int,
    var count:Int
):BaseEntity(){
    constructor(user:User, hashTag: HashTag,score:Int,count:Int):this(0,user,hashTag,score,count)
}