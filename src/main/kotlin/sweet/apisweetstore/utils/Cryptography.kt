package sweet.apisweetstore.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object Cryptography {
    fun convertPasswordToSHA512(password: String): String {
        var md: MessageDigest? = null
        try {
            md = MessageDigest.getInstance("SHA-512")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        val hash = BigInteger(1, md?.digest(password.toByteArray()))
        return hash.toString(16)
    }
}