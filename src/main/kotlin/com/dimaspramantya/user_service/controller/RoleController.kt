package com.dimaspramantya.user_service.controller

import com.dimaspramantya.user_service.domain.dto.response.ResGetAllRoleDto
import com.dimaspramantya.user_service.service.MasterRoleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/roles")
class RoleController(
    private val masterRoleService: MasterRoleService
) {
    @GetMapping("/all")
    fun getAllRole(): ResponseEntity<List<ResGetAllRoleDto>>{
        return ResponseEntity.ok(
            masterRoleService.getAllRole()
        )
    }

}