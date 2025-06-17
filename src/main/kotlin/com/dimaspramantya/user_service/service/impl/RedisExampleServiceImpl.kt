package com.dimaspramantya.user_service.service.impl

import com.dimaspramantya.user_service.exception.CustomException
import com.dimaspramantya.user_service.repository.MasterUserRepository
import com.dimaspramantya.user_service.service.RedisExampleService
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisExampleServiceImpl(
    private val stringRedisTemplate : StringRedisTemplate,
    private val userRepository: MasterUserRepository

): RedisExampleService {

    override fun set(userId: Int): String {
        val user = userRepository.findById(userId).orElseThrow {
            throw CustomException("User Not Found", 404)
        }

        val operationSetring = stringRedisTemplate.opsForValue()

        operationSetring.set("user-service:user:username:${user.id}", user.username, Duration.ofMinutes(60))
        return "User dengan username ${user.username} berhasil fetching dari redis"
    }

    override fun get(userId: Int): String {

        val user = userRepository.findById(userId).orElseThrow {
            throw CustomException("User Not Found", 404)
        }

        val operationString = stringRedisTemplate.opsForValue()

        val username = operationString.get("user-service:user:username:${user.id}") ?:
        throw CustomException("Username Not Found Redis",404)

        return "Username ID ${user.id}, Name ${username}";
    }
}
