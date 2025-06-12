package com.dimaspramantya.user_service.exception

class CustomException(
    val exceptionMessage: String,
    val statusCode: Int,
    val data: Any? = null
): RuntimeException()