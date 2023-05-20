package com.ahrorovk.testmangofzco.presentation.AuthorizationScreen

import com.ahrorovk.testmangofzco.core.states.SendAuthCodeState

sealed class AuthorizationEvent {
    data class OnSelectedNumberRegionOfPhoneChange(val number: Int) : AuthorizationEvent()
    data class OnSelectedImageRegionOfPhoneChange(val image: Int) : AuthorizationEvent()
    data class OnPhoneChange(val phone: String) : AuthorizationEvent()
    data class OnSendAuthCodeChange(val authCodeState: SendAuthCodeState): AuthorizationEvent()
    object SendAuthCode: AuthorizationEvent()
    object GoToRegistration: AuthorizationEvent()
}