package com.dimaspramantya.user_service.service.impl

import com.dimaspramantya.user_service.domain.constant.Constant
import com.dimaspramantya.user_service.domain.dto.request.ReqLoginDto
import com.dimaspramantya.user_service.domain.dto.request.ReqRegisterDto
import com.dimaspramantya.user_service.domain.dto.request.ReqUpdateUserDto
import com.dimaspramantya.user_service.domain.dto.response.ResGetUsersDto
import com.dimaspramantya.user_service.domain.dto.response.ResLoginDto
import com.dimaspramantya.user_service.domain.entity.MasterUserEntity
import com.dimaspramantya.user_service.exception.CustomException
import com.dimaspramantya.user_service.repository.MasterRoleRepository
import com.dimaspramantya.user_service.repository.MasterUserRepository
import com.dimaspramantya.user_service.service.MasterUserService
import com.dimaspramantya.user_service.util.BCryptUtil
import com.dimaspramantya.user_service.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*
import kotlin.Int

@Service
class MasterUserServiceImpl(
    private val masterUserRepository: MasterUserRepository,
    private val masterRoleRepository: MasterRoleRepository,
    private val httpServletRequest: HttpServletRequest,
    private val jwtUtil: JwtUtil,
    private val bcrypt: BCryptUtil
): MasterUserService {
    override fun findAllActiveUsers(): List<ResGetUsersDto> {
        val rawData = masterUserRepository.getAllActiveUser()
        val result = mutableListOf<ResGetUsersDto>()
        //GET ALL USER
        rawData.forEach { u ->
            result.add(
                ResGetUsersDto(
                    username = u.username,
                    id = u.id,
                    email = u.email,
                    //jika user memiliki role maka ambil id role
                    //jika user tidak memiliki role maka value null
                    //GET ROLE BY USER.role_id
                    roleId = u.role?.id,
//                    //jika user memilikie role maka ambil name role
//                    roleName = u.role?.name
                )
            )
        }
        return result
    }

    override fun register(req: ReqRegisterDto): ResGetUsersDto {
        val role = if(req.roleId == null){
            Optional.empty() //berarti optional.IsEmpty bernilai true
            //berbeda dengan null
        }else{
            masterRoleRepository.findById(req.roleId)
        }

        if(role.isEmpty && req.roleId != null){
            throw CustomException("Role ${req.roleId} tidak ditemukan", 400)
        }

        //cek apakah email sudah terdaftar
        val existingUserEmail = masterUserRepository.findFirstByEmail(req.email)
        if(existingUserEmail != null){
            try {
                throw CustomException("Email sudah terdaftar", 400)
            }catch (e: Exception){
                println("oops")
            }
        }

        val existingUsername = masterUserRepository
            .findFirstByUsername(req.username)

        if(existingUsername.isPresent){
            throw CustomException("Username sudah terdaftar", 400)
        }

        val hashPw = bcrypt.hash(req.password)

        val userRaw = MasterUserEntity(
            email = req.email,
            password = hashPw,
            username = req.username,
            role = if(role.isPresent){
                role.get()
            }else{
                null
            }
        )
        //entity/row dari hasil save di jadikan sebagi return value
        val user = masterUserRepository.save(userRaw)
        return ResGetUsersDto(
            id = user.id,
            email = user.email,
            username = user.username,
            roleId = user.role?.id
        )
    }

    override fun login(req: ReqLoginDto): ResLoginDto {
        val userEntityOpt = masterUserRepository.findFirstByUsername(req.username)

        if (userEntityOpt.isEmpty) {
            throw CustomException("Username atau Password salah", 400)
        }

        val userEntity = userEntityOpt.get()

        if (!bcrypt.verify(req.password, userEntity.password)) {
            throw CustomException("Username atau Password salah", 400)
        }

        val role = if (userEntity.role != null) {
            userEntity.role!!.name
        } else {
            "user"
        }

        val token = jwtUtil.generateToken(userEntity.id, role)

        return ResLoginDto(token)
    }
    @Cacheable("getUserById",key = "{#id}")
    override fun findUser(id: Int): ResGetUsersDto {
        val user = masterUserRepository.getDetailUser(id);

        if(user == null){
            throw CustomException("Id tidak ditemukan",400)
        }

        if (!user.isActive || user.isDelete) {
            throw CustomException("User tidak aktif atau sudah dihapus", 403)
        }

        return ResGetUsersDto(
            id = user.id,
            email = user.email,
            username = user.username,
            roleId = user.role?.id
        )
    }

    @CacheEvict(
        value = ["getUserById"],
        key = "{#userId}"
    )
    override fun updateUser(req: ReqUpdateUserDto, userId:Int): ResGetUsersDto {
        val userId = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
//        val userRole = httpServletRequest.getHeader(Constant.HEADER_USER_ROLE)


        val user = masterUserRepository.findById(userId.toInt()).orElseThrow {
            throw CustomException("Tidak ada user yang ditemukan",400)
        }

        val existingUser = masterUserRepository.findFirstByUsername(req.username)
        if(existingUser.isPresent){
            if(existingUser.get().id != user.id){
                throw CustomException("Email sudah Terdaftar",400)
            }
        }

        val existingUserEmail = masterUserRepository.findFirstByEmail(req.email)
        if(existingUserEmail != null){
            if(existingUserEmail.id != user.id){
                throw CustomException("Email sudah ditemukan",400)
            }
        }

        user.email = req.email
        user.username = req.username
        user.updatedBy = userId

        val result = masterUserRepository.save(user)

        return ResGetUsersDto(
            id = userId.toInt(),
            email = result.email,
            username = result.username

        )

    }

    @CacheEvict(
        value = ["getUserById"],
        key = "{#id}"
    )
    override fun softDeleteUserById(id: Int) {
        val userId = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
        val roleId = httpServletRequest.getHeader(Constant.HEADER_USER_ROLE)
        if(roleId.toInt() != 1){
            throw CustomException("Tidak bisa hapus kecuali admin", 400)
        }
        val now = Timestamp(System.currentTimeMillis())
        val deleted = masterUserRepository.softDeleteById(
            id = id,
            deletedAt = now,
            deletedBy = userId
        )

        if(deleted == 0){
            throw CustomException("User tidak ditemukan",400)
        }
    }

    @CacheEvict(
        value = ["getUserById"],
        key = "{#id}"
    )
    override fun hardDeleteUserById(id: Int) {
        val userId = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
        val roleId = httpServletRequest.getHeader(Constant.HEADER_USER_ROLE)
        if(roleId.toInt() != 1){
            throw CustomException("Tidak bisa hapus kecuali admin", 403)
        }
        val delete = masterUserRepository.hardDeleteById(id)
        if(delete == 0){
            throw CustomException("User tidak ditemukan",400)
        }

    }


}