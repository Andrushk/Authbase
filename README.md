# Authbase
Помогает создать свою аутентификацию в Андроид приложении

{
initialize(ctx: Context, authProvider: IAuthProvider)
}

True - если текущий пользователь вообще есть и если вход выполнен
{
registered: LiveData<Boolean>
}
  
Получить профиль текущего пользователя
{
getProfile(): LiveData<Resource<UserProfile>>
}

Осуществить вход по логину и паролю
{
signIn(login: String, password: String): LiveData<Resource<UserProfile>>
}

Разлогиниться. Вернет True - если все прошло успешно.
{
logoff(): LiveData<Resource<Any?>>
}

Получить интент для активити с логином/паролем
{
getSignInIntent(): Intent
}
