package com.dimaspramantya.user_service.service

import com.dimaspramantya.user_service.domain.dto.response.ResGetAllRoleDto

interface MasterRoleService {
    fun getAllRole(): List<ResGetAllRoleDto>
}