package com.github.signed.sealed.signed

import com.github.signed.sealed.dto.User
import com.github.signed.sealed.file.FileHelper
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.nio.file.Paths
import java.security.SignedObject
import java.time.LocalDate


object SigningTest : Spek({
    given ("Signing service") {
        val signingService = SigningService("test.p12", "test", "changeit".toCharArray())
        val user = User("Jan", "Kowalski", LocalDate.of(1984, 1, 22))
        val userDataPath = Paths.get("target/user.data")

        beforeGroup {
            Paths.get("target").toFile().mkdirs()
            userDataPath.toFile().delete()
        }

        on("signing of object") {

            it("should be signed without errors") {
                signingService.sign(user)
            }

            it("should be verified with same certificate") {
                val signed = signingService.sign(user)
                signingService.verify(signed).shouldBeTrue()
                val (firstName, lastName, birthDate) = signed.`object` as User
                firstName `should be equal to` "Jan"
                lastName `should be equal to` "Kowalski"
                birthDate `should equal` LocalDate.of(1984, 1, 22)
            }

        }

        on("interaction with FS") {

            it("should be saved to file") {
                val signed = signingService.sign(user)
                FileHelper.save(signed, userDataPath)
                userDataPath.toFile().exists().shouldBeTrue()
            }

            it("should be read") {
                val signed = FileHelper.read(userDataPath, SignedObject::class.java)
                signingService.verify(signed).shouldBeTrue()
                signed.`object` `should equal` user
            }

        }
    }
})
