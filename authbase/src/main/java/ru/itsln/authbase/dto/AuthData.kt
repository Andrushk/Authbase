package ru.itsln.authbase.dto

/**
 * Базовый класс для аутентификационных данных
 */
abstract class AuthData {
    /**
     * True, данная аутентификация актуальна (пользователь подключен)
     */
    abstract fun isAlive(): Boolean
}