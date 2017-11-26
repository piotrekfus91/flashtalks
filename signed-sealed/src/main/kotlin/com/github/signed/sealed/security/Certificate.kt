package com.github.signed.sealed.security

import com.github.signed.sealed.signed.SigningService
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Certificate(pkcs12Path: String, alias: String, password: CharArray) {
    val publicKey: PublicKey
    val privateKey: PrivateKey
    val encryptingCipher: Cipher
    val decryptingCipher: Cipher
    val signature: Signature = Signature.getInstance("NONEwithRSA")

    init {
        // init keys
        val keystore = KeyStore.getInstance("PKCS12")
        keystore.load(SigningService::class.java.classLoader.getResourceAsStream(pkcs12Path), password)
        publicKey = keystore.getCertificate(alias).publicKey
        privateKey = keystore.getKey(alias, password) as PrivateKey

        // init AES secret
        val secretBytes = ByteArray(16)
        Random().nextBytes(secretBytes)
        val secret = SecretKeySpec(secretBytes, "AES")

        // init ciphers
        encryptingCipher = Cipher.getInstance("AES")
        encryptingCipher.init(Cipher.ENCRYPT_MODE, secret)
        decryptingCipher = Cipher.getInstance("AES")
        decryptingCipher.init(Cipher.DECRYPT_MODE, secret)
    }
}
