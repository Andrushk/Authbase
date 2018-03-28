package ru.itsln.authbase.contract

import android.arch.lifecycle.LiveData
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.dto.UserProfile

internal interface IProfileRepo {
    /**
     * Получить профиль пользователя
     */
    fun get(): LiveData<Resource<UserProfile>>

    /**
     * Записать профиль пользователя и получить обратно записанный
     */
    fun set(profile: UserProfile): LiveData<Resource<UserProfile>>

    /**
     * Очистить профиль (удалить данные о регистрации)
     */
    fun clear(): LiveData<Resource<Any?>>
}