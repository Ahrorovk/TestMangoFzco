package com.ahrorovk.testmangofzco.presentation.ProfileScreen

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.ahrorovk.testmangofzco.core.states.CurrentUserState
import com.ahrorovk.testmangofzco.core.states.InfoStates
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UpdateUserInfoState
import com.ahrorovk.testmangofzco.domain.model.requestResponse.Avatars
import com.ahrorovk.testmangofzco.domain.model.requestResponse.ProfileData

data class ProfileState(
    val phone:String="",
    val itemState:String = "",
    val typeOfItem:String = "",
    val accessToken:String="",
    val accessTokenLife:Long=0,
    val refreshToken:String="",
    val refreshState:Boolean=false,
    val refreshTokenState: RefreshTokenState = RefreshTokenState(),
    val updateUserInfoState: UpdateUserInfoState = UpdateUserInfoState(),
    val currentUserState: CurrentUserState=CurrentUserState(),
    val avatar: String="https://sb-smart.ru/wp-content/uploads/f/0/c/f0c1f068a7cc5bd338ecad0cfd6a8b84.png",
    val avatars: Avatars = Avatars("","",""),
    val birthday: String="2023-05-18",
    val city: String="",
    val completed_task: Int =0,
    val created: String="",
    val id: Int=0,
    val instagram: String="",
    val last: String="",
    val name: String="",
    val online: Boolean = false,
    val status: String="",
    val username: String="",
    val vk: String=""
)