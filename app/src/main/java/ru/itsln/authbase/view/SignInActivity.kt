package ru.itsln.authbase.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import ru.itsln.authbase.R
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.appContextUI

/**
 * A login screen that offers login via login/password.
 */
class SignInActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private var mAuthTask: UserLoginTask? = null
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = SignInViewModel.create(this, appContextUI)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        sign_in_button.setOnClickListener { _ ->
            forceHideKeybord()
            attemptLogin()
        }
    }

    override fun onStart() {
        super.onStart()
        loadUser()
    }

    private fun loadUser(){
        viewModel.getCurrentUser().observe(this,  Observer { resource ->

            val user = parseResource(resource)
            if (user!=null)
                login.setText(user.name)
        } )
    }

    /**
     * Извлечь из "Resource<T>" объект "Т", обработать ошибки
     */
    private fun <T>parseResource(res: Resource<T>?) : T? {
        if (res == null) {
            noteError(null)
            return null
        }

        if (res.status == Resource.Status.ERROR) {
            noteError(res.throwable?.message)
            return null
        }

        return res.data
    }

    /**
     * Записать ошибку в лог и показать пользователю
     */
    private fun noteError(message: String?) {
        val m = message ?: "null"
        Log.e("SignInActivity", m)
        info.text = m
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
//        if (mAuthTask != null) {
//            return
//        }

        // Reset errors.
        login.error = null
        password.error = null
        info.text=""

        // Store values at the time of the login attempt.
        val loginStr = login.text.toString()
        val passwordStr = password.text.toString().trim()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a login.
        if (TextUtils.isEmpty(loginStr)) {
            login.error = getString(R.string.error_field_required)
            focusView = login
            cancel = true
        } else if (!isLoginValid(loginStr)) {
            login.error = getString(R.string.error_invalid_email)
            focusView = login
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true)
//            mAuthTask = UserLoginTask(loginStr, passwordStr)
//            mAuthTask!!.execute(null as Void?)
            viewModel.signIn(loginStr, passwordStr).observe(this, Observer { resource ->
                showProgress(false)
                val token = parseResource(resource)
                if (token!=null) finish()
             })
        }
    }

    private fun isLoginValid(logn: String): Boolean {
        //TODO: реализовать логику проверки логина
        return login.length() > 1
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 0
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    private fun forceHideKeybord(){
        //from: https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    inner class UserLoginTask internal constructor(private val mLogin: String, private val mPassword: String) : AsyncTask<Void, Void, Boolean>() {
//
//        override fun doInBackground(vararg params: Void): Boolean? {
//            // TODO: attempt authentication against a network service.
//            UserExtended.login(mLogin, mPassword)
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000)
//            } catch (e: InterruptedException) {
//                return false
//            }
//
//            // пока считаем, что авторизация на сервере прошла норм
//            return true
//        }
//
//        override fun onPostExecute(success: Boolean?) {
//            mAuthTask = null
//            showProgress(false)
//
//            if (success!!) {
//                finish()
//            } else {
//                password.error = getString(R.string.error_incorrect_password)
//                password.requestFocus()
//            }
//        }
//
//        override fun onCancelled() {
//            mAuthTask = null
//            showProgress(false)
//        }
//    }
}
