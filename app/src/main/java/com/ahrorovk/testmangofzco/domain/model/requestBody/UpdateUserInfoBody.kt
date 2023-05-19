package com.ahrorovk.testmangofzco.domain.model.requestBody

data class UpdateUserInfoBody(
    val avatar: Avatar?,
    val birthday: String?,
    val city: String?,
    val instagram: String?,
    val name: String?,
    val status: String?,
    val username: String?,
    val vk: String?
)