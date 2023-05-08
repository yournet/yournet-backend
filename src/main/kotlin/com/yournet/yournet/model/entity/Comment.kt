package com.yournet.yournet.model.entity

import javax.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Int = 0,

    @Column
    var content: String,

    @ManyToOne
    var post: Post,
): BaseEntity() {
}