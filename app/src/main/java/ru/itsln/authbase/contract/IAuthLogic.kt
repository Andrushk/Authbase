package ru.itsln.authbase.contract

import android.arch.lifecycle.LiveData
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.dto.UserProfile

/**
 * Контракт БЛ
 */
internal interface IAuthLogic {
    /**
     * Проверка осуществлен вход или нет. Без подробностей, просто Да или Нет.
     */
    fun isRegistered(): LiveData<Boolean>

    /**
     * Получить профиль текущего пользователя
     */
    fun getProfile(): LiveData<Resource<UserProfile>>

    /**
     * Осуществить вход по логину и паролю
     */
    fun signIn(login: String, password: String): LiveData<Resource<UserProfile>>

    /**
     * Разлогиниться
     */
    fun logoff(): LiveData<Resource<Any?>>
}