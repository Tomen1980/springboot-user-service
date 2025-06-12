package com.dimaspramantya.user_service.repository

import com.dimaspramantya.user_service.domain.entity.MasterUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface MasterUserRepository: JpaRepository<MasterUserEntity, Int> {
    @Query("""
        SELECT U FROM MasterUserEntity U
        WHERE U.isDelete = false
        AND U.isActive = true
    """, nativeQuery = false)
    fun getAllActiveUser(): List<MasterUserEntity>
    fun findFirstByEmail(email: String): MasterUserEntity?
    fun findFirstByUsername(username: String): Optional<MasterUserEntity>

    @Query("""
    SELECT u FROM MasterUserEntity u
    LEFT JOIN FETCH u.role r
    WHERE u.id = :id
""")
    fun getDetailUser(@Param("id") id: Int): MasterUserEntity?
}