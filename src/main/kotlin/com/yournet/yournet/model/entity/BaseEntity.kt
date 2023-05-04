package com.yournet.yournet.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity(

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PrePersist
    fun prePersist() {
        this.createdAt = LocalDateTime.now()
        this.updatedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        this.updatedAt = LocalDateTime.now()
    }
}