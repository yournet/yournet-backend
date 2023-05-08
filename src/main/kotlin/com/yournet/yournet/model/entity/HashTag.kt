package com.yournet.yournet.model.entity

import javax.persistence.*

@Entity
@Table(name = "HashTag")
class HashTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var hashTagId: Int = 0,

    @Column(nullable = false)
    var hashTagName: String,

    @OneToMany(mappedBy = "hashTag")
    var postHashtag: MutableList<PostHashtag> = mutableListOf(),


): BaseEntity() {
}