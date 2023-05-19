package com.ahrorovk.testmangofzco.app.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UpdateUserInfoState
import com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen.AuthCodeCheckEvent
import com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen.AuthCodeCheckScreen
import com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen.AuthCodeCheckViewModel
import com.ahrorovk.testmangofzco.presentation.AuthorizationScreen.AuthorizationEvent
import com.ahrorovk.testmangofzco.presentation.AuthorizationScreen.AuthorizationScreen
import com.ahrorovk.testmangofzco.presentation.AuthorizationScreen.AuthorizationViewModel
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileEvent
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileScreen
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileViewModel
import com.ahrorovk.testmangofzco.presentation.RegistrationScreen.RegistrationEvent
import com.ahrorovk.testmangofzco.presentation.RegistrationScreen.RegistrationScreen
import com.ahrorovk.testmangofzco.presentation.RegistrationScreen.RegistrationViewModel
import java.time.LocalDateTime
import java.util.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.RegistrationScreen.route) {
        composable(Screens.RegistrationScreen.route) {
            val viewModel = hiltViewModel<RegistrationViewModel>()
            val state = viewModel.state.collectAsState()
            val currentTime = System.currentTimeMillis()
            val tenMinutesAgo = currentTime / 60000
            val accessTokenLife = state.value.accessTokenLife / 60000
            if (tenMinutesAgo - accessTokenLife <= (9)) {
                LaunchedEffect(key1 = true) {
                    Log.e(
                        "LIFE",
                        "${state.value.accessTokenLife}\n${(tenMinutesAgo - accessTokenLife)}"
                    )
                    navController.navigate(Screens.ProfileScreen.route) {
                        popUpTo(Screens.RegistrationScreen.route) {
                            inclusive = true
                        }
                    }
                    return@LaunchedEffect
                }
            } else if(tenMinutesAgo - accessTokenLife > (9) && accessTokenLife>0){
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(RegistrationEvent.RefreshToken)
                }
                if (state.value.userRegState.response != null) {
                    LaunchedEffect(key1 = true) {
                        Log.e(
                            "LIFE",
                            "${state.value.accessTokenLife}\n${(tenMinutesAgo - accessTokenLife)}"
                        )
                        navController.navigate(Screens.ProfileScreen.route) {
                            popUpTo(Screens.RegistrationScreen.route) {
                                inclusive = true
                            }
                        }
                        return@LaunchedEffect
                    }
                }
            }
            RegistrationScreen(state = state.value, onEvent = { event ->
                when (event) {
                    is RegistrationEvent.GoToAuthorization -> {
                        navController.navigate(Screens.AuthorizationScreen.route)
                    }
                    is RegistrationEvent.GoToProfile -> {
                        navController.navigate(Screens.ProfileScreen.route) {
                            popUpTo(Screens.RegistrationScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
            )
        }
        composable(Screens.AuthorizationScreen.route) {
            val viewModel = hiltViewModel<AuthorizationViewModel>()
            val state = viewModel.state.collectAsState()
            AuthorizationScreen(state = state.value, onEvent = { event ->
                when (event) {
                    is AuthorizationEvent.GoToRegistration -> {
                        navController.popBackStack()
                    }
                    is AuthorizationEvent.GoToSecondStepOfAuth -> {
                        navController.navigate(Screens.AuthCodeCheckScreen.route)
                    }
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
            )
        }
        composable(Screens.AuthCodeCheckScreen.route) {
            val viewModel = hiltViewModel<AuthCodeCheckViewModel>()
            val state = viewModel.state.collectAsState()
            AuthCodeCheckScreen(state = state.value, onEvent = { event ->
                when (event) {
                    is AuthCodeCheckEvent.GoToProfile -> {
                        navController.navigate(Screens.ProfileScreen.route) {
                            popUpTo(Screens.AuthCodeCheckScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                    is AuthCodeCheckEvent.GoToRegistration->{
                        navController.navigate(Screens.RegistrationScreen.route){
                            popUpTo(Screens.AuthCodeCheckScreen.route){
                                inclusive=true
                            }
                        }
                    }
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
            )
        }
        composable(Screens.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val state = viewModel.state.collectAsState()
            val context = LocalContext.current
            if(state.value.updateUserInfoState.error!=""){
                LaunchedEffect(key1 = true){
                    Toast.makeText(context,state.value.updateUserInfoState.error, Toast.LENGTH_LONG).show()
                }
            }
            if(state.value.refreshTokenState.response!=null) {
                LaunchedEffect(key1 =true) {
                    viewModel.onEvent(ProfileEvent.GetCurrentUser)
                    viewModel.onEvent(ProfileEvent.ChangeItem)
                    viewModel.onEvent(ProfileEvent.OnRefreshTokenResponseChange(RefreshTokenState()))
                }
            }
            if (state.value.updateUserInfoState.response != null) {
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(ProfileEvent.GetCurrentUser)
                    viewModel.onEvent(ProfileEvent.OnRefreshTokenResponseChange(RefreshTokenState()))
                    viewModel.onEvent(ProfileEvent.OnUpdateUserInfoChange(UpdateUserInfoState()))
                }
            }
            LaunchedEffect(key1 = true){
                viewModel.onEvent(ProfileEvent.GetCurrentUser)
            }
            ProfileScreen(state = state.value, onEvent = { event ->
                when (event) {
                    is ProfileEvent.LogOutFromAccount->{
                        navController.navigate(Screens.RegistrationScreen.route){
                            popUpTo(Screens.ProfileScreen.route){
                                inclusive=true
                            }
                        }
                    }
                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
            )
        }
    }
}