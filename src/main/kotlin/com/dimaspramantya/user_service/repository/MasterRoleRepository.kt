package com.dimaspramantya.user_service.repository

import com.dimaspramantya.user_service.domain.entity.MasterRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MasterRoleRepository: JpaRepository<MasterRoleEntity, Int> {
}