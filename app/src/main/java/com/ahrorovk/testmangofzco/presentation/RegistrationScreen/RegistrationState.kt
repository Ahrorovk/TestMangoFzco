package com.ahrorovk.testmangofzco.presentation.RegistrationScreen

import com.ahrorovk.testmangofzco.R
import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UserRegistrationState

data class RegistrationState (
    val phone:String = "",
    val name:String = "",
    val userName:String = "",
    val refresh_token:String = "",
    val accessToken:String = "",
    val accessTokenLife:Long = 0,
    val userRegState:UserRegistrationState = UserRegistrationState(),
    val refreshTokenState:RefreshTokenState = RefreshTokenState(),
    val selectedNumberRegionOfPhone:Int = 7,
    val selectedImageRegionOfPhone:Int = R.drawable.flag_ru,
    val suggestionsItems:List<Pair<Int,Int>> = listOf(Pair(992, R.drawable.flag_tj), Pair(7, R.drawable.flag_ru)),
    val error:String = ""
)