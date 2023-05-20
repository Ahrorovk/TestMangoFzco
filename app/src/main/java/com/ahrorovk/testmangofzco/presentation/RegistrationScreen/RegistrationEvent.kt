package com.ahrorovk.testmangofzco.presentation.RegistrationScreen

import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UserRegistrationState

sealed class RegistrationEvent {
    data class OnPhoneChange(val phone:String):RegistrationEvent()
    data class OnRefreshTokenChange(val token:String):RegistrationEvent()
    data class OnAccessTokenChange(val token:String):RegistrationEvent()
    data class OnAccessTokenLifeChange(val lifeData:Long):RegistrationEvent()
    data class OnNameChange(val name:String):RegistrationEvent()
    data class OnUserNameChange(val userName:String):RegistrationEvent()
    data class OnErrorChange(val error: String):RegistrationEvent()
    data class OnSelectedNumberRegionOfPhoneChange(val selectedNumberRegionOfPhone:Int):RegistrationEvent()
    data class OnSelectedImageRegionOfPhoneChange(val selectedImageRegionOfPhone:Int):RegistrationEvent()
    data class OnUserRegistrationChange(val user:UserRegistrationState):RegistrationEvent()
    data class OnRefreshTokenResponseChange(val token:RefreshTokenState):RegistrationEvent()
    object SendUserRegistration:RegistrationEvent()
    object RefreshToken:RegistrationEvent()
    object GoToAuthorization:RegistrationEvent()
}