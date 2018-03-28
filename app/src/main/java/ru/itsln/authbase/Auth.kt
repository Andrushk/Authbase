package ru.itsln.authbase

import android.arch.lifecycle.LiveData
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.dto.UserProfile

/**
 * Публикует наружу все доступные функции связанные с аутентификацией
 */
class Auth {
    //TODO: статиком сделать?
    private lateinit var appContext: IAppContext

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
     * TODO: перегрузить другими способами входа
     */
    fun signin(login: String, password: String): LiveData<Resource<UserProfile>> {
        return appContext.authLogic.signIn(login, password)
    }

    /**
     * Разлогиниться. Вернет True - если все прошло успешно.
     */
    fun logoff(): LiveData<Resource<Any?>> {
        return appContext.authLogic.logoff();
    }

    //TODO: fun getSignInIntent()

    companion object {
        internal fun init(appContext: IAppContext): Auth = Auth().apply { this.appContext = appContext }
        //TODO: fun int(Context, IAuthProvider)
    }
}