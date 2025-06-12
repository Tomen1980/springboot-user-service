package com.dimaspramantya.user_service.domain.dto.response

data class ResGetUsersDto(
    val id: Int,
    val email: String,
    val username: String,
    var roleId: Int? = null,
//    var roleName: String? = null
)
