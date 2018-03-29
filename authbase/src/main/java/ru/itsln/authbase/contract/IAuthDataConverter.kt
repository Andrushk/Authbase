package ru.itsln.authbase.contract

import ru.itsln.authbase.dto.AuthData

interface IAuthDataConverter {
    fun toJson(authData: AuthData): String
    fun fromJson(json: String): AuthData
}