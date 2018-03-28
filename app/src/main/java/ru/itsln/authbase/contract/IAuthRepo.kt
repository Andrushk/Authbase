package ru.itsln.authbase.contract

import android.arch.lifecycle.LiveData
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.dto.AuthData

interface IAuthRepo {
    fun authenticate(login: String, password: String): LiveData<Resource<AuthData>>
}