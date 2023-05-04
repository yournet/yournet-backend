package com.yournet.yournet.model.payload.auth.response

import java.time.LocalDateTime

data class UserResponseDto(
    val userId: Int?,
    val name: String?,
    val email: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)