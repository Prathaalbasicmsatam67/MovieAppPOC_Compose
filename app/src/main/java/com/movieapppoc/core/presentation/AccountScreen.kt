package com.movieapppoc.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movieapppoc.movielist.util.Screen
import com.movieapppoc.signin.presentation.SignInScreen
import com.movieapppoc.signup.presentation.SignUpScreen
import com.movieapppoc.signup.presentation.TermsAndCondition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.shadow(2.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.SignUp.rout
            ) {

                composable(Screen.TermsAndCondition.rout) {
                    TermsAndCondition()
                }

                composable(Screen.SignIn.rout) {
                    SignInScreen(navigateToSignUp = {
                        navController.navigate(Screen.SignUp.rout) {
                            popUpTo(navController.graph.id)
                        }
                    }, onSignInSuccess = {
                        navController.navigate(Screen.Home.rout) {
                            popUpTo(navController.graph.id)
                        }
                    })
                }
                composable(Screen.SignUp.rout) {
                    SignUpScreen(navController = navController)
                }
            }
        }

    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen()
}