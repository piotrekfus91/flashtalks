package com.github.signed.sealed.signed

import com.github.signed.sealed.dto.User
import com.github.signed.sealed.sealed.SealingService
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.dsl.xon
import java.security.SignedObject
import java.time.LocalDate
import javax.crypto.SealedObject

object WorkingTogetherTest : Spek({
    given("Both services") {
        val pkcs12Path = "test.p12"
        val alias = "test"
        val password = "changeit".toCharArray()

        val signingService = SigningService(pkcs12Path, alias, password)
        val sealingService = SealingService(pkcs12Path, alias, password)

        val user = User("Jan", "Kowalski", LocalDate.of(1984, 1, 22))

        on("signing first") {
            val signed = signingService.sign(user)
            val sealed = sealingService.seal(signed)

            it("should be signed inside sealed object") {
                val readFromSealed = sealingService.read<SignedObject>(sealed)
                signingService.verify(readFromSealed).shouldBeTrue()
                readFromSealed.`object` `should equal` user
            }
        }

        xon("sealing first") {
            val sealed = sealingService.seal(user)
            val signed = signingService.sign(sealed)

            it("should be sealed inside signed object") {
                signingService.verify(signed).shouldBeTrue()
                sealingService.read<User>(signed.`object` as SealedObject) `should equal` user
            }
        }
    }
})
