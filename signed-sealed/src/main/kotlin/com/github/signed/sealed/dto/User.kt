package com.github.signed.sealed.dto

import java.io.Serializable
import java.time.LocalDate

data class User (
        var firstName: String,
        var lastName: String,
        var birthDate: LocalDate
) : Serializable
