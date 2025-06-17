package com.dimaspramantya.user_service.service

interface RedisExampleService {
    fun set(userId: Int): String
    fun get(userId: Int): String
}