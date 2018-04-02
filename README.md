# Authbase
Помогает создать свою аутентификацию в Андроид приложении

<br>

### Доступные функции:

<br>

Без инициализации ничего не заработает. Обязательно необходимо передать фактическую реализацию AuthProvider 
```
initialize(ctx: Context, authProvider: IAuthProvider)
```
<br>

True - если текущий пользователь вообще есть и если вход выполнен
```
registered: LiveData<Boolean>
```
<br>
   
Получить профиль текущего пользователя
```
getProfile(): LiveData<Resource<UserProfile>>
```
<br>
 
Осуществить вход по логину и паролю
```
signIn(login: String, password: String): LiveData<Resource<UserProfile>>
```
<br>
 
Разлогиниться. Вернет True - если все прошло успешно.
```
logoff(): LiveData<Resource<Any?>>
```
<br>
 
Получить интент для активити с логином/паролем
```
getSignInIntent(): Intent
```
