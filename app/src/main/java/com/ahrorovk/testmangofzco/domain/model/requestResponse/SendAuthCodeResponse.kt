package com.ahrorovk.testmangofzco.domain.model.requestResponse

data class SendAuthCodeResponse(
    val is_success:Boolean,
    val detail:String,
    val body:String
)
