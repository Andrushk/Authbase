package ru.itsln.authbase

import android.content.Context
import ru.itsln.authbase.contract.IAuthProvider
import ru.itsln.authbase.implementation.AuthAppContext

/**
 * Точка входа
 */
class AuthBuilder(private val ctx: Context) {

    private lateinit var authProvider: IAuthProvider

    fun setAuthProvider(provider: IAuthProvider): AuthBuilder {
        authProvider = provider
        return this
    }

    /**
     * Создает объект для управления аутентификацией
     */
    fun build(): Auth {
        return Auth.init(AuthAppContext(ctx, authProvider))
    }
}