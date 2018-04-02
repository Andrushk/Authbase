package ru.itsln.authbase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import ru.itsln.authbase.accessories.Resource
import ru.itsln.authbase.contract.IAppContext
import ru.itsln.authbase.contract.IProfileRepo
import ru.itsln.authbase.dto.AuthData
import ru.itsln.authbase.dto.UserProfile
import ru.itsln.authbase.implementation.AuthLogic

@RunWith(MockitoJUnitRunner::class)
class AuthLogicTest {
    @Mock
    private lateinit var mockAppContext: IAppContext

    /**
     * this bypasses the main thread check,
     * and immediately runs any tasks on your test thread,
     * allowing for immediate and predictable calls and therefore assertions
     */
    @Suppress("unused")
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun isRegistered_UnknownFail() {
        //если где-то там внутри происходит какая-то ошибка,
        //то isRegistered обязан вернуть false
        isRegistered_assert(Resource.error(Throwable("что-то пошло не так...")))
                .onChanged(false)
    }

    @Test
    fun isRegistered_NoData(){
        //если профиль пользователя пустой, то isRegistered обязан вернуть false
        isRegistered_assert(Resource.success(null))
                .onChanged(false)
    }

    @Test
    fun isRegistered_Alive(){
        val aliveAuthData: AuthData = mock()
        `when`(aliveAuthData.isAlive()).thenReturn(true)

        //профиль есть, и AuthData жива и актуальна (isAlive=true),
        // значит isRegistered обязан вернуть true
        isRegistered_assert(Resource.success(UserProfile("", aliveAuthData)))
                .onChanged(true)
    }

    private fun isRegistered_assert(testedSourceData: Resource<UserProfile>): Observer<Boolean> {
        val mockProfileRepo = mock(IProfileRepo::class.java)
        `when`(mockProfileRepo.get())
                .thenReturn(MutableLiveData<Resource<UserProfile>>()
                        .apply { value = testedSourceData })
        `when`(mockAppContext.profileRepository)
                .thenReturn(mockProfileRepo)

        val observer: Observer<Boolean> = mock()
        AuthLogic(mockAppContext).isRegistered().observeForever(observer)
        return observer
    }

    inline fun <reified T: Any> mock() = Mockito.mock(T::class.java)
}