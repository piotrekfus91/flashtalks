package com.github.signed.sealed.sealed

import com.github.signed.sealed.security.Certificate
import java.io.Serializable
import javax.crypto.SealedObject

class SealingService(pkcs12Path: String, alias: String, password: CharArray) {
    val certificate = Certificate(pkcs12Path, alias, password)

    fun <T : Serializable> seal(data: T): SealedObject = SealedObject(data, certificate.encryptingCipher)

    fun <T : Serializable> read(sealedObject: SealedObject): T = sealedObject.getObject(certificate.decryptingCipher) as T
}
