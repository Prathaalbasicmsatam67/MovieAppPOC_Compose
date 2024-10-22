package com.movieapppoc.signup.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.movieapppoc.signup.domain.SignUpEvent
import com.movieapppoc.signup.domain.SignUpUIState
import com.movieapppoc.signup.domain.UIEvent
import com.movieapppoc.signup.domain.Validator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val TAG = SignUpViewModel::class.java.name

    // difference with compose state and StateFlow https://www.youtube.com/watch?v=T8vApYJlW8o
    // Recommended to use stateflow as it is useful to use same viewmodel in some other NonComposable classes
    var signUpUIState = mutableStateOf(SignUpUIState())
        private set


    var loadingProgressState = mutableStateOf(false)

    val signUpEvent = MutableSharedFlow<SignUpEvent>()


    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.FirstNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    firstName = event.firstName,
                    fNameError = Validator.validateFirstName(
                        event.firstName
                    )
                )
            }

            is UIEvent.EmailChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    email = event.email,
                    emailError = Validator.validateEmailName(
                        event.email
                    )
                )
            }

            is UIEvent.LastNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    lastName = event.lastName,
                    lNameError = Validator.validateLastName(
                        event.lastName
                    )
                )
            }

            is UIEvent.PasswordChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    password = event.password,
                    passError = Validator.validatePassword(
                        event.password
                    )
                )
            }

            is UIEvent.RegisterButtonClicked -> {
                signUpAction()
            }
            else -> Unit
        }
    }

    private fun signUpAction() {
        Log.d(SignUpViewModel::class.java.name, " OnSignUpClick $signUpUIState")
        validateAndProcess()
    }

    private fun validateAndProcess() {
        val fNameResult = Validator.validateFirstName(
            signUpUIState.value.firstName
        )
        val lNameResult = Validator.validateLastName(
            signUpUIState.value.lastName
        )
        val emailResult = Validator.validateEmailName(
            signUpUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            signUpUIState.value.password
        )

        if (fNameResult && lNameResult && emailResult && passwordResult) {
            println("xxx show error")
        } else {
            // process sign operation
            println("xxx process sign in operation")
            createUserInFirebase(
                signUpUIState.value.email,
                signUpUIState.value.password
            )
        }
    }

    private fun createUserInFirebase(email: String, password: String) {
        loadingProgressState.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                loadingProgressState.value = false
                if (it.isSuccessful) {
                    Log.d(TAG, "on complete call")
                    viewModelScope.launch {
                        signUpEvent.emit(SignUpEvent.Success)
                    }

                }
            }
            .addOnFailureListener {
                loadingProgressState.value = false
                Log.d(TAG, "on fail call")
                signUpUIState.value = signUpUIState.value.copy(
                    signUpError = it.localizedMessage?.toString() ?: ""
                )
            }
    }
}