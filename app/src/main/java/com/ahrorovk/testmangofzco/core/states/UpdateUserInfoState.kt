package com.ahrorovk.testmangofzco.core.states

import com.ahrorovk.testmangofzco.domain.model.requestResponse.UpdateUserInfoResponse

data class UpdateUserInfoState(
    val isLoading:Boolean = false,
    val response:UpdateUserInfoResponse? = null,
    val error:String = ""
)
