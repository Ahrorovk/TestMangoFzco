package com.ahrorovk.testmangofzco.presentation.ProfileScreen

import com.ahrorovk.testmangofzco.core.states.CurrentUserState
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UpdateUserInfoState
import com.ahrorovk.testmangofzco.domain.model.requestBody.Avatar
import com.ahrorovk.testmangofzco.domain.model.requestResponse.ProfileData
import java.io.File

sealed class ProfileEvent {
    data class OnCurrentUserStateChange(val state:CurrentUserState):ProfileEvent()
    data class OnUpdateUserInfoChange(val state: UpdateUserInfoState):ProfileEvent()
    data class OnAccessTokenChange(val token:String):ProfileEvent()
    data class OnRefreshTokenResponseChange(val state: RefreshTokenState):ProfileEvent()
    data class OnAccessTokenLifeChange(val lifeData:Long):ProfileEvent()
    data class OnInfoStatesChange(
        val avatar: String?,
        val birthday: String?,
        val city: String?,
        val instagram: String?,
        val name: String?,
        val status: String?,
        val username: String?,
        val vk: String?
    ):ProfileEvent()
    data class OnItemChange(val change:String):ProfileEvent()
    data class OnLogOutFromAccount(val accessToken:String,val refreshToken:String,val lifeData: Long):ProfileEvent()
    data class GoToChange(val screen:String):ProfileEvent()
    data class OnRefreshTokenChange(val token:String):ProfileEvent()
    data class OnRefreshStateChange(val state:Boolean):ProfileEvent()
    data class OnUserInfoChange(val info:ProfileData):ProfileEvent()
    object GetCurrentUser:ProfileEvent()
    object LogOutFromAccount:ProfileEvent()
    object ChangeItem:ProfileEvent()
    object RefreshToken:ProfileEvent()

}