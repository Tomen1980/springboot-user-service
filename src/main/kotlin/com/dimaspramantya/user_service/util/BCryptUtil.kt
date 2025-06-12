package com.dimaspramantya.user_service.util

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class BCryptUtil {
    fun hash(password: String?): String {
        return BCrypt.hashpw(password, BCrypt.gensalt(11))
    }

    fun verify(password: String?, hash: String?): Boolean {
        return BCrypt.checkpw(password, hash)
    }
}