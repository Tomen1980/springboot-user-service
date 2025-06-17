package com.dimaspramantya.user_service.service

import com.dimaspramantya.user_service.domain.dto.request.ReqRegisterDto
import com.dimaspramantya.user_service.domain.dto.request.ReqLoginDto
import com.dimaspramantya.user_service.domain.dto.request.ReqUpdateUserDto
import com.dimaspramantya.user_service.domain.dto.response.ResGetUsersDto
import com.dimaspramantya.user_service.domain.dto.response.ResLoginDto

interface MasterUserService {
    fun findAllActiveUsers(): List<ResGetUsersDto>
    fun register(req: ReqRegisterDto): ResGetUsersDto
    fun login(req: ReqLoginDto): ResLoginDto
    fun findUser(id:Int): ResGetUsersDto
    fun updateUser(req: ReqUpdateUserDto, userId:Int): ResGetUsersDto
    fun softDeleteUserById(id: Int)
    fun hardDeleteUserById(id:Int)

}