package com.dimaspramantya.user_service.repository

import com.dimaspramantya.user_service.domain.entity.MasterUserEntity
import jakarta.transaction.Transactional
import jdk.jfr.Timestamp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
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

    @Modifying
    @Transactional
    @Query("""
        UPDATE MasterUserEntity u
        SET u.isDelete = true,
            u.isActive = false,
            u.deletedAt = :deletedAt,
            u.deletedBy = :deletedBy
        WHERE u.id = :id
    """)
    fun softDeleteById(id: Int, deletedAt: java.sql.Timestamp, deletedBy: String): Int

    @Modifying
    @Transactional
    @Query("DELETE FROM MasterUserEntity u WHERE u.id = :id")
    fun hardDeleteById(@Param("id") id: Int): Int
}