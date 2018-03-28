package ru.itsln.authbase.implementation

import android.content.Context
import android.content.SharedPreferences
import ru.itsln.authbase.AUTH_PREFS_FILENAME
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.contract.IAuthProvider
import ru.itsln.authbase.contract.IAuthLogic
import ru.itsln.authbase.contract.IAuthRepo
import ru.itsln.authbase.contract.IProfileRepo

/**
 * Фактическая реализация IAppContext
 */
internal class AuthAppContext(private val ctx: Context, private val authProvider: IAuthProvider): IAppContext {
    /**
     * Настройки
     */
    override val preferences: SharedPreferences by lazy {
        ctx.getSharedPreferences(AUTH_PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    /**
     * БЛ
     */
    override val authLogic: IAuthLogic by lazy {
        AuthLogic(this)
    }

    /**
     * Репозитории
     */
    override val profileRepository: IProfileRepo by lazy {
        LocalProfileRepo(preferences, authProvider.authDataConverter)
    }

    override val authRepository: IAuthRepo by lazy {
        authProvider.authRepository
    }
}