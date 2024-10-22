package com.movieapppoc.signin.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.movieapppoc.R
import com.movieapppoc.core.presentation.common_components.AppPasswordTextField
import com.movieapppoc.core.presentation.common_components.AppTextField
import com.movieapppoc.core.presentation.common_components.ButtonComponent
import com.movieapppoc.core.presentation.common_components.ClickableLoginTextComponent
import com.movieapppoc.core.presentation.common_components.HeadingTextComponent
import com.movieapppoc.core.presentation.common_components.NormalTextComponent
import com.movieapppoc.movielist.util.Screen
import com.movieapppoc.signup.domain.UIEvent
import com.movieapppoc.signup.presentation.DividerComponent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    navigateToSignUp: (() -> Unit)? = null,
    onSignInSuccess: (() -> Unit)? = null,
    signInViewModel: SignInViewModel = viewModel()
) {

    val uiState = signInViewModel.signinState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        signInViewModel.signInEvent.collectLatest {
            if(it is SignInEvent.Success) {
                Toast.makeText(
                    context,
                    "LoggedIn Successfully !",
                    Toast.LENGTH_SHORT
                ).show()

                onSignInSuccess?.invoke()
            } else {
                Toast.makeText(
                    context,
                    "Invalid Details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {

        NormalTextComponent(value = stringResource(R.string.hello))
        HeadingTextComponent(value = stringResource(R.string.sign_in))


        Spacer(modifier = Modifier.height(20.dp))

        AppTextField(
            labelValue = stringResource(R.string.email),
            imageVector = Icons.Filled.Email,
            isError = signInViewModel.signinState.value.emailError,
            textValue = uiState.value.email,
            onTextChange = {
                signInViewModel.onEvent(UIEvent.EmailChanged(it))
            }
        )
        AppPasswordTextField(
            labelValue = stringResource(R.string.password),
            imageVector = Icons.Filled.Password,
            onTextChange = { signInViewModel.onEvent(UIEvent.PasswordChanged(it)) }
        )

        Spacer(modifier = Modifier.heightIn(50.dp))


        ButtonComponent("Sign In") {
            signInViewModel.onEvent(UIEvent.SignInButtonClicked)
        }

        Spacer(modifier = Modifier.heightIn(30.dp))

        DividerComponent()

        Spacer(modifier = Modifier.heightIn(20.dp))

        ClickableLoginTextComponent(
            initialText = stringResource(R.string.login_screen_signup_initial),
            clickableText = stringResource(R.string.login_screen_sign_up)
        ) {
            navigateToSignUp?.invoke()
        }
    }
}

@Preview
@Composable
fun SignInPreview() {
    SignInScreen()
}