package com.ahrorovk.testmangofzco.presentation.AuthorizationScreen

import com.ahrorovk.testmangofzco.R
import com.ahrorovk.testmangofzco.core.states.SendAuthCodeState
import com.ahrorovk.testmangofzco.core.states.UserRegistrationState

data class AuthorizationState (
    val token:String ="",
    val phone:String = "",
    val selectedNumberRegionOfPhone:Int = 7,
    val selectedImageRegionOfPhone:Int = R.drawable.flag_ru,
    val suggestionsItems:List<Pair<Int,Int>> = listOf(Pair(992, R.drawable.flag_tj), Pair(7, R.drawable.flag_ru)),
    val error:String = "",
    val authCodeSendState: SendAuthCodeState = SendAuthCodeState(),
    )