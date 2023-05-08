package com.yournet.yournet.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
class PostHashTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Int = 0,

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="hashTagId")
    @JsonBackReference
    var hashTag: HashTag,

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="postId")
    @JsonBackReference
    var post: Post,

): BaseEntity() {

}