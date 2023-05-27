package com.yournet.yournet.model.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

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
    var postHashtag: MutableList<PostHashTag>? = mutableListOf(),

    @OneToMany(mappedBy = "post")
    var comment: MutableList<Comment>? = mutableListOf(),

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    var user: User?,


    @Column
    var likeCount: Int = 0,

    @OneToMany(mappedBy = "post")
    var like: MutableList<Like>? = mutableListOf(),

):BaseEntity() {
}