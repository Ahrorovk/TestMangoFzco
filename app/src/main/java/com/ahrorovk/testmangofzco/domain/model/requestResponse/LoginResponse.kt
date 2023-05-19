package com.ahrorovk.testmangofzco.domain.model.requestResponse

import com.ahrorovk.testmangofzco.domain.model.requestBody.UserBody

data class LoginResponse(
    val success:Boolean,
    val user:UserBody,
    val jwt:String
)
