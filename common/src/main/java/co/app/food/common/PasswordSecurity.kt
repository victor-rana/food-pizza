package co.app.food.common

import java.lang.Exception
import javax.crypto.Cipher

import javax.crypto.spec.SecretKeySpec

object PasswordSecurity {
    @Throws(Exception::class)
    fun encrypt(raw: ByteArray, clear: ByteArray): ByteArray? {
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        return cipher.doFinal(clear)
    }

    @Throws(Exception::class)
    fun decrypt(raw: ByteArray, encrypted: ByteArray): ByteArray? {
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        return cipher.doFinal(encrypted)
    }
}
