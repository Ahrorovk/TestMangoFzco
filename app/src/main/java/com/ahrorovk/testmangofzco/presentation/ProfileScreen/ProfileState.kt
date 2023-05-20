package com.ahrorovk.testmangofzco.presentation.ProfileScreen

import android.graphics.Bitmap
import com.ahrorovk.testmangofzco.core.states.CurrentUserState
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UpdateUserInfoState
import com.ahrorovk.testmangofzco.domain.model.requestResponse.Avatars

data class ProfileState(
    val phone:String="",
    val itemState:String = "",
    val typeOfItem:String = "",
    val accessToken:String="",
    val avatarBitmap: Bitmap? = null,
    val accessTokenLife:Long=0,
    val refreshToken:String="",
    val refreshState:Boolean=false,
    val refreshTokenState: RefreshTokenState = RefreshTokenState(),
    val updateUserInfoState: UpdateUserInfoState = UpdateUserInfoState(),
    val currentUserState: CurrentUserState=CurrentUserState(),
    val avatar: String="media/avatars/400x400/d6a4c63ba1a14b0ea9855f351b5edd69.png",
    val avatarBase64: String="",
    val avatars: Avatars = Avatars(),
    val birthday: String="2023-05-18",
    val city: String="",
    val instagram: String="",
    val name: String="",
    val status: String="",
    val username: String="",
    val vk: String=""
)