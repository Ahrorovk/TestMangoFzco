package com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen

import com.ahrorovk.testmangofzco.core.states.CheckAuthCodeState

sealed class AuthCodeCheckEvent {
    data class OnCodeChange(val code:String):AuthCodeCheckEvent()
    data class OnStateCheckAuthCodeChange(val state:CheckAuthCodeState):AuthCodeCheckEvent()
    data class OnAccessTokenChange(val token:String):AuthCodeCheckEvent()
    data class OnAccessTokenLifeChange(val lifeData:Long):AuthCodeCheckEvent()
    data class OnRefreshTokenChange(val token:String):AuthCodeCheckEvent()
    object CheckAuthCode:AuthCodeCheckEvent()
}