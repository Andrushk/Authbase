package ru.itsln.authbase.accessories

/**
 * Обертка для данных передаваемых через LiveData
 */
class Resource<T> private constructor(val status: Status, val data: T?, val throwable: Throwable?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(throwable: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, throwable)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
