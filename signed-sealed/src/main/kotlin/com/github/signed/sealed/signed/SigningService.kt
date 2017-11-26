package com.github.signed.sealed.signed

import com.github.signed.sealed.security.Certificate
import java.io.Serializable
import java.security.SignedObject

class SigningService(pkcs12Path: String, alias: String, password: CharArray) {
    private val certificate = Certificate(pkcs12Path, alias, password)

    fun <T : Serializable> sign(data: T): SignedObject = SignedObject(data, certificate.privateKey, certificate.signature)

    fun verify(signedObject: SignedObject): Boolean = signedObject.verify(certificate.publicKey, certificate.signature)
}
