package com.ahrorovk.testmangofzco.app.navigation


sealed class Screens(val route: String) {
    object RegistrationScreen : Screens("RegistrationScreen")
    object AuthorizationScreen : Screens("AuthorizationScreen")
    object AuthCodeCheckScreen : Screens("AuthCodeCheckScreen")
    object ProfileScreen : Screens("ProfileScreen")
}