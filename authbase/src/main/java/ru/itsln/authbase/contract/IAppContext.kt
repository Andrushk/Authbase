package ru.itsln.authbase.contract

import android.content.Context
import android.content.SharedPreferences

internal interface IAppContext {
    val context: Context

    //Настройки
    val preferences: SharedPreferences

    //БЛ
    val authLogic: IAuthLogic

    //Репозитории
    val profileRepository: IProfileRepo
    val authRepository: IAuthRepo
}