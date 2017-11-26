package com.github.signed.sealed.signed

import com.github.signed.sealed.dto.User
import com.github.signed.sealed.file.FileHelper
import com.github.signed.sealed.sealed.SealingService
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.nio.file.Paths
import java.time.LocalDate
import javax.crypto.SealedObject


object SealingTest : Spek({
    given ("Sealing service") {
        val sealingService = SealingService("test.p12", "test", "changeit".toCharArray())
        val user = User("Jan", "Kowalski", LocalDate.of(1984, 1, 22))
        val userDataPath = Paths.get("target/sealed.data")

        beforeGroup {
            Paths.get("target").toFile().mkdirs()
            userDataPath.toFile().delete()
        }

        on("sealing of object") {

            it("should be sealed without errors") {
                sealingService.seal(user)
            }

            it("should be read with same certificate") {
                val signed = sealingService.seal(user)
                val (firstName, lastName, birthDate) = sealingService.read<User>(signed)
                firstName `should be equal to` "Jan"
                lastName `should be equal to` "Kowalski"
                birthDate `should equal` LocalDate.of(1984, 1, 22)
            }

        }

        on("interaction with FS") {

            it("should be saved to file") {
                val signed = sealingService.seal(user)
                FileHelper.save(signed, userDataPath)
                userDataPath.toFile().exists().shouldBeTrue()
            }

            it("should be read") {
                val signed = FileHelper.read(userDataPath, SealedObject::class.java)
                sealingService.read<User>(signed) `should equal` user
            }

        }
    }
})
