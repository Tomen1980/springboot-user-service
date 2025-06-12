package com.dimaspramantya.user_service.domain.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp

@Entity
@Table(name = "mst_users")
data class MasterUserEntity(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "mst_users_id_seq"
    )
    @SequenceGenerator(
        name = "mst_users_id_seq", // name used in @GeneratedValue
        sequenceName = "mst_users_id_seq", // name of sequence in DB
        allocationSize = 1 // adjust based on how your DB increments
    )
    @Column(name = "id", insertable = false, updatable = false)
    var id: Int = 0,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "username")
    var username: String,

    @ManyToOne(fetch = FetchType.LAZY)
    //foreign key direpresentasikan sebagai kolom role_id didalam table user
    @JoinColumn(name = "role_id")
    var role: MasterRoleEntity? = null,

    @Column(name = "created_by")
    var createdBy: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", insertable = false, updatable = false)
    var createdAt: Timestamp? = null,

    @Column(name = "updated_by")
    var updatedBy: String? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false, updatable = false)
    var updatedAt: Timestamp? = null,

    @Column(name = "deleted_at")
    var deletedAt: Timestamp? = null,

    @Column(name = "deleted_by")
    var deletedBy: String? = null,

    @Column(name = "is_active")
    var isActive: Boolean = true,

    @Column(name = "is_delete")
    var isDelete: Boolean = false
)
