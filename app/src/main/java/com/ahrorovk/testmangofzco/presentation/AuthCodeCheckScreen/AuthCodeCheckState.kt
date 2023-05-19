package com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen

import com.ahrorovk.testmangofzco.core.states.CheckAuthCodeState

data class AuthCodeCheckState (
    val code:String = "",
    val phone:String = "",
    val checkAuthCodeState:CheckAuthCodeState = CheckAuthCodeState()
)