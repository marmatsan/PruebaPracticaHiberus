package com.example.androidtechnicaltestcanary.utils

enum class InputError {
    EMPTY_NAME,
    EMPTY_PASSWORD,
    EMPTY_EMAIL,
    EMPTY_DATE,

    SHORT_NAME,
    SHORT_PASSWORD,

    INCORRECT_EMAIL_FORMAT,

    NO_ERROR;

    fun getErrorMessage(): String {
        return when (this) {
            EMPTY_NAME -> "Campo requerido"
            EMPTY_PASSWORD -> "Campo requerido"
            EMPTY_EMAIL -> "Campo requerido"
            EMPTY_DATE -> "Campo requerido"
            SHORT_NAME -> "El nombre debe tener al menos dos palabras"
            SHORT_PASSWORD -> "La contraseña debe tener al menos 6 caracteres"
            INCORRECT_EMAIL_FORMAT -> "Formato de email no correcto"
            else -> {
                "Error desconocido"
            }
        }
    }

    fun isError(): Boolean {
        return this != NO_ERROR
    }
}