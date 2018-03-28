package ru.itsln.authbase.implementation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.os.AsyncTask
import ru.itsln.authbase.AUTH_PREFS_USER_AUTH_DATA
import ru.itsln.authbase.AUTH_PREFS_USER_NAME
import ru.itsln.authbase.AUTH_PREFS_USER_NAME_DEFAULT
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.contract.IAuthDataConverter
import ru.itsln.authbase.dto.UserProfile
import ru.itsln.authbase.contract.IProfileRepo

/**
 * Репозиторий для работы с локальным профилем пользователя
 */
//TODO: нужна реализация работающая не только с токенами, но и, скажем, с куками
internal class LocalProfileRepo(
        private val preferences: SharedPreferences,
        private val converter: IAuthDataConverter): IProfileRepo {

    override fun get(): LiveData<Resource<UserProfile>> {
        val result = MutableLiveData<Resource<UserProfile>>()
        AsyncTask.execute({
            try {
                val user = load(preferences)
                result.postValue(Resource.success(user))
            } catch (e: Throwable) {
                result.postValue(Resource.error(e))
            }
        })
        return result
    }

    override fun set(profile: UserProfile): LiveData<Resource<UserProfile>> {
        val result = MutableLiveData<Resource<UserProfile>>()

        AsyncTask.execute({
            //TODO: try-catch надо прикрутить, если ошибка - ругаться в LiveData
            val editor = preferences.edit()
            editor.putString(AUTH_PREFS_USER_NAME, profile.name)
            editor.putString(AUTH_PREFS_USER_AUTH_DATA, converter.toJson(profile.authData))
            editor.apply()
            result.postValue(Resource.success(load(preferences)))
        })

        return result
    }

    override fun clear(): LiveData<Resource<Any?>> {
        val result = MutableLiveData<Resource<Any?>>()

        AsyncTask.execute({
            //TODO: try-catch надо прикрутить, если ошибка - ругаться в LiveData
            val editor = preferences.edit()
            editor.putString(AUTH_PREFS_USER_AUTH_DATA, "")
            editor.apply()
            result.postValue(Resource.success(null))
        })

        return result
    }

    private fun load(preferences: SharedPreferences): UserProfile {
        return UserProfile(
                preferences.getString(AUTH_PREFS_USER_NAME, AUTH_PREFS_USER_NAME_DEFAULT),
                converter.fromJson(preferences.getString(AUTH_PREFS_USER_AUTH_DATA, "")))
    }
}