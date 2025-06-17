package com.dimaspramantya.user_service.controller

import com.dimaspramantya.user_service.domain.dto.response.BaseResponse
import com.dimaspramantya.user_service.service.RedisExampleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/redis")
class RedisExampleController(
    private val redisExampleService: RedisExampleService
) {

    @PostMapping("/set/{userId}")
    fun setValue(
        @PathVariable userId: Int
    ): ResponseEntity<BaseResponse<String>>
    {
        return ResponseEntity.ok(
            BaseResponse(
                data = redisExampleService.set(userId)
            )
        )
    }

    @GetMapping("/get/{id}")
    fun getValue(
        @PathVariable userId: Int
    ): ResponseEntity<BaseResponse<String>>
    {
        return ResponseEntity.ok(
            BaseResponse(
                data = redisExampleService.get(userId)
            )
        )
    }
}