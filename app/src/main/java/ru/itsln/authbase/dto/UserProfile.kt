package ru.itsln.authbase.dto

import ru.itsln.authbase.dto.AuthData

data class UserProfile (
        val name: String,
        val authData: AuthData)