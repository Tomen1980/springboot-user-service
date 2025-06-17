package com.dimaspramantya.user_service.controller

import com.dimaspramantya.user_service.domain.constant.Constant
import com.dimaspramantya.user_service.domain.dto.request.ReqLoginDto
import com.dimaspramantya.user_service.domain.dto.request.ReqRegisterDto
import com.dimaspramantya.user_service.domain.dto.request.ReqUpdateUserDto
import com.dimaspramantya.user_service.domain.dto.response.BaseResponse
import com.dimaspramantya.user_service.domain.dto.response.ResGetUsersDto
import com.dimaspramantya.user_service.domain.dto.response.ResLoginDto
import com.dimaspramantya.user_service.service.MasterUserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val masterUserService: MasterUserService,
    private val httpServletRequest: HttpServletRequest
) {
    @GetMapping("/active")
    fun getAllActiveUser(): ResponseEntity<BaseResponse<List<ResGetUsersDto>>>{
        return ResponseEntity.ok(
            BaseResponse(
                data = masterUserService.findAllActiveUsers()
            )
        )
    }
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody req: ReqRegisterDto
    ): ResponseEntity<BaseResponse<ResGetUsersDto>>{
        return ResponseEntity(
            BaseResponse(
                data = masterUserService.register(req),
                message = "Register sukses"
            ),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/get-user/{id}")
    fun getDetailUser(@PathVariable id: Int): ResponseEntity<BaseResponse<ResGetUsersDto>> {
        return ResponseEntity(
            BaseResponse(
                data = masterUserService.findUser(id),
                message = "Get Detail User Sukses"
            ),
            HttpStatus.OK
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody req: ReqLoginDto
    ): ResponseEntity<BaseResponse<ResLoginDto>>{
        return ResponseEntity(
            BaseResponse(
                data = masterUserService.login(req),
                message = "Login Sukses"
            ),
            HttpStatus.OK
        )
    }

    @PutMapping("update-user")
    fun updateUser(
        @RequestBody req: ReqUpdateUserDto
    ): ResponseEntity<BaseResponse<ResGetUsersDto>>{
        val userId = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
        return ResponseEntity(
            BaseResponse(
                data = masterUserService.updateUser(req,userId.toInt()),
                message = "Login Sukses"
            ),
            HttpStatus.OK
        )
    }

    @DeleteMapping("soft-delete/{id}")
    fun softDeleteUserById(
        @PathVariable id: Int
    ): ResponseEntity<BaseResponse<String?>>{
        val userId = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
        masterUserService.softDeleteUserById(id)
        return ResponseEntity(
            BaseResponse(
                message = "Success Delete User"
            ),
            HttpStatus.OK
        )

    }

    @DeleteMapping("hard-delete/{id}")
    fun hardDeleteUserById(
        @PathVariable id: Int
    ): ResponseEntity<BaseResponse<String?>>{
        val userId = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
        masterUserService.hardDeleteUserById(id)
        return ResponseEntity(
            BaseResponse(
                message = "Success Hard Delete User "
            ),
            HttpStatus.OK
        )

    }

}