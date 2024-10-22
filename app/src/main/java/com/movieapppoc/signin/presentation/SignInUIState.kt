package com.movieapppoc.signin.presentation

data class SignInUIState(
    val email : String = "",
    val password : String = "",
    var signInError: String = "",
    val emailError : Boolean = false,
    val passError : Boolean = false,
)

sealed class SignInEvent() {
    data object Success : SignInEvent()
    data object Failure : SignInEvent()
}
