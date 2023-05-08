package com.yournet.yournet.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Int = 0,

    @Column
    var title: String,

    @Column
    var content: String,

    @Column
    var postImage: String?,

    @OneToMany(mappedBy = "post")
    var postHashtag: MutableList<PostHashtag> = mutableListOf(),

    @OneToMany(mappedBy = "post")
    var comment: MutableList<Comment> = mutableListOf(),

):BaseEntity() {
}