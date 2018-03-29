package ru.itsln.authbase.contract

interface IAuthProvider {
    val authRepository: IAuthRepo
    val authDataConverter: IAuthDataConverter
}