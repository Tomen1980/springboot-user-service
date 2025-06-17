package com.dimaspramantya.user_service.domain.dto.response

import java.io.Serializable

data class ResGetUsersDto(
    val id: Int,
    val email: String,
    val username: String,
    var roleId: Int? = null,
//    var roleName: String? = null
): Serializable {
    companion object {
        private const val serialVersionUID: Long = 8783090186345917598L
    }
}
