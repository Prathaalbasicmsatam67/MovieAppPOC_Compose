package com.movieapppoc.signup.domain

// https://kotlinlang.org/docs/sealed-classes.html#declare-a-sealed-class-or-interface
sealed interface UIEvent {

    data class FirstNameChanged(val firstName: String) : UIEvent
    data class LastNameChanged(val lastName: String) : UIEvent
    data class EmailChanged(val email: String) : UIEvent
    data class PasswordChanged(val password: String) : UIEvent

    data object RegisterButtonClicked : UIEvent
    data object SignInButtonClicked : UIEvent
}

sealed interface SignUpEvent {
    data object Success : SignUpEvent
}

