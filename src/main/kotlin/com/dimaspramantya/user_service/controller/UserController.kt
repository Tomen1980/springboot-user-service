package com.dimaspramantya.user_service.controller

import com.dimaspramantya.user_service.domain.dto.request.ReqRegisterDto
import com.dimaspramantya.user_service.domain.dto.response.BaseResponse
import com.dimaspramantya.user_service.domain.dto.response.ResGetUsersDto
import com.dimaspramantya.user_service.service.MasterUserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val masterUserService: MasterUserService
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
}