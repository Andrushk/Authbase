package ru.itsln.authbase.implementation

import android.arch.lifecycle.*
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.contract.IAuthLogic
import ru.itsln.authbase.dto.AuthData
import ru.itsln.authbase.dto.UserProfile


internal class AuthLogic(private val appContext: IAppContext): IAuthLogic {

    override fun isRegistered(): LiveData<Boolean> {
        return Transformations.map(appContext.profileRepository.get()) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> resource.data?.authData?.isAlive() ?: false
                else -> false
            }
        }
    }

    override fun getProfile(): LiveData<Resource<UserProfile>> {
        //TODO: на надо каждый раз грузить профиль, надо его помнить
        return appContext.profileRepository.get()
    }

    override fun signIn(login: String, password: String): LiveData<Resource<UserProfile>> {
        val mediator = MediatorLiveData<Resource<UserProfile>>()

        // получить сохраненный профиль (модель) и передать дальше UserProfile
        val profileObserver = Observer<Resource<UserProfile>>({ p ->
            mediator.value = p

//            when (p?.status) {
//                Resource.Status.SUCCESS ->
//                    mediator.value = Resource.success(UserProfileData().apply { name = p.data?.name?:""
//                    })
//                else -> mediator.value = Resource.error(p?.throwable)
//            }
        })

        // получить ответ от сервера авторизации (токен) и сохранить профиль
        val authObserver = Observer<Resource<AuthData>>({ r ->
            when (r?.status) {
                Resource.Status.SUCCESS -> {
                    //TODO: проверку на r==null или вообще избавиться от null
                    //TODO: нафик тут каждый раз создавать UserProfile?? может надо его помнить?

                    mediator.addSource(appContext.profileRepository.set(UserProfile(login, r.data!!)), profileObserver)

                }
                else -> mediator.value = Resource.error(r?.throwable)
            }
        })

        mediator.addSource(appContext.authRepository.authenticate(login, password), authObserver)
        return mediator
    }

    override fun logoff(): LiveData<Resource<Any?>> {
        //на сервер надо что-то отправлять??

        //чистим профиль
        return appContext.profileRepository.clear()
    }
}