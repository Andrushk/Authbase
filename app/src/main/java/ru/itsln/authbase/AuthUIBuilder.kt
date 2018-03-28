package ru.itsln.authbase

import android.content.Context
import android.content.Intent
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.contract.IAuthProvider
import ru.itsln.authbase.implementation.AuthAppContext
import ru.itsln.authbase.view.SignInActivity

/**
 * Билдер Intent'а для вызова Activity с логином
 */
class AuthUIBuilder {
    lateinit var ctx: Context
    lateinit var authProvider: IAuthProvider

    fun build(): Intent {
        appContextUI = AuthAppContext(ctx, authProvider)
        val i =  Intent()
        return Intent(ctx, SignInActivity::class.java)
    }
}

// это некрасивая жопа, но как иначе передать в Активити нужный IAppContext?
internal lateinit var appContextUI: IAppContext
