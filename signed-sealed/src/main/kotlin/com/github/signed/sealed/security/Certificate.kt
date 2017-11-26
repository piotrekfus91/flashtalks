package com.github.signed.sealed.security

import com.github.signed.sealed.signed.SigningService
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

class Certificate(pkcs12Path: String, alias: String, password: CharArray) {
    val publicKey: PublicKey
    val privateKey: PrivateKey
    val signature = Signature.getInstance("NONEwithRSA")

    init {
        val keystore = KeyStore.getInstance("PKCS12")
        keystore.load(SigningService::class.java.classLoader.getResourceAsStream(pkcs12Path), password)
        publicKey = keystore.getCertificate(alias).publicKey
        privateKey = keystore.getKey(alias, password) as PrivateKey
    }
}
