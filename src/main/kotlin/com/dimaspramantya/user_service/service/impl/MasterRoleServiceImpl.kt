package com.dimaspramantya.user_service.service.impl

import com.dimaspramantya.user_service.domain.dto.response.ResGetAllRoleDto
import com.dimaspramantya.user_service.repository.MasterRoleRepository
import com.dimaspramantya.user_service.service.MasterRoleService
import org.springframework.stereotype.Service

@Service
class MasterRoleServiceImpl(
    private val masterRoleRepository: MasterRoleRepository
): MasterRoleService {
    override fun getAllRole(): List<ResGetAllRoleDto> {
        val rawRole = masterRoleRepository.findAll()
        val result = mutableListOf<ResGetAllRoleDto>()
        rawRole.forEach { role ->
            result.add(
                ResGetAllRoleDto(
                    id = role.id,
                    name = role.name
                )
            )
        }
        return result
    }
}