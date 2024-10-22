package com.movieapppoc.signin.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.movieapppoc.signup.domain.SignUpEvent
import com.movieapppoc.signup.domain.UIEvent
import com.movieapppoc.signup.domain.Validator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    val signinState = mutableStateOf(SignInUIState())


    var loadingProgressState = mutableStateOf(false)

    val signInEvent = MutableSharedFlow<SignInEvent>()

    fun onEvent(event: UIEvent) {
        when (event) {

            is UIEvent.EmailChanged -> {
                signinState.value = signinState.value.copy(
                    email = event.email,
                    emailError = Validator.validateEmailName(
                        event.email
                    )
                )
            }

            is UIEvent.PasswordChanged -> {
                signinState.value = signinState.value.copy(
                    password = event.password,
                    passError = Validator.validatePassword(
                        event.password
                    )
                )
            }
            is UIEvent.SignInButtonClicked -> validateAndProcess()

            else -> Unit
        }
    }

    private fun validateAndProcess() {
        val emailResult = Validator.validateEmailName(
            signinState.value.email
        )
        val passwordResult = Validator.validatePassword(
            signinState.value.password
        )

        if (emailResult && passwordResult) {
            println("xxx show error")
        } else {
            // process sign operation
            println("xxx process sign in operation")
            signUserInFirebase(
                signinState.value.email,
                signinState.value.password
            )
        }
    }

    private fun signUserInFirebase(email: String, password: String) {
        loadingProgressState.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                loadingProgressState.value = false
                if (it.isSuccessful) {
                    println("on complete call" + it.result.user?.email)
                    viewModelScope.launch {
                        signInEvent.emit(SignInEvent.Success)
                    }
                }
            }
            .addOnFailureListener {
                loadingProgressState.value = false
                println("on fail call")
                signinState.value = signinState.value.copy(
                    signInError = it.localizedMessage?.toString() ?: ""
                )
                viewModelScope.launch {
                    signInEvent.emit(SignInEvent.Failure)
                }
            }
    }
}