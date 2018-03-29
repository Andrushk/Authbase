package ru.itsln.authbase.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.dto.UserProfile

internal class SignInViewModel : ViewModel() {

    private lateinit var appContext: IAppContext

    fun getCurrentUser(): LiveData<Resource<UserProfile>> {
        return appContext.authLogic.getProfile()
    }

    fun signIn(name: String, password: String): LiveData<Resource<UserProfile>> {
        return appContext.authLogic.signIn(name, password)
    }

    companion object {
        fun create(activity: FragmentActivity, appContext: IAppContext): SignInViewModel {
            var viewModel = ViewModelProviders.of(activity).get(SignInViewModel::class.java)
            viewModel.appContext = appContext
            return viewModel
        }
    }
}