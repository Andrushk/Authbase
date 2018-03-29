package ru.itsln.authbase

import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.contract.IAuthProvider
import ru.itsln.authbase.dto.UserProfile
import ru.itsln.authbase.implementation.AuthAppContext
import ru.itsln.authbase.view.SignInActivity

/**
 * Точка входа.
 * Здесь публикуюся наружу все доступные функции связанные с аутентификацией.
 */

internal const val AUTH_PREFS_FILENAME = "auth.prefs"
internal const val AUTH_PREFS_USER_NAME = "Username"
internal const val AUTH_PREFS_USER_NAME_DEFAULT = "Superstar"
internal const val AUTH_PREFS_USER_AUTH_DATA = "AuthData"

internal lateinit var appContext: IAppContext

fun initialize(ctx: Context, authProvider: IAuthProvider){
    appContext = AuthAppContext(ctx, authProvider)
}

/**
 * True - если текущий пользователь вообще есть и если вход выполнен
 */
val registered: LiveData<Boolean>
    get() = appContext.authLogic.isRegistered()

/**
 * Получить профиль текущего пользователя
 */
fun getProfile(): LiveData<Resource<UserProfile>> {
    return appContext.authLogic.getProfile()
}

/**
 * Осуществить вход по логину и паролю
 */
fun signIn(login: String, password: String): LiveData<Resource<UserProfile>> {
    return appContext.authLogic.signIn(login, password)
}

/**
 * Разлогиниться. Вернет True - если все прошло успешно.
 */
fun logoff(): LiveData<Resource<Any?>> {
    return appContext.authLogic.logoff();
}

/**
 * Получить интент для активити с логином/паролем
 */
fun getSignInIntent(): Intent {
    return Intent(appContext.context, SignInActivity::class.java)
}