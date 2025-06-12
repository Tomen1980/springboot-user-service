package com.dimaspramantya.user_service.domain.dto.request

import jakarta.validation.constraints.NotBlank

data class ReqRegisterDto(
    @field:NotBlank(message = "username wajib diisi")
    val username: String,
    @field:NotBlank(message = "email wajib diisi")
    val email: String,
    @field:NotBlank(message = "password wajib diisi")
    val password: String,
    val roleId: Int?
)
