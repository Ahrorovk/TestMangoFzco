package com.ahrorovk.testmangofzco.domain.model.requestResponse

data class ProfileData(
    val avatar: String?=null,
    val avatars: Avatars? = null,
    val birthday: String?=null,
    val city: String?=null,
    val completed_task: Int =0,
    val created: String="",
    val id: Int=0,
    val instagram: String?=null,
    val last: String="",
    val name: String="",
    val online: Boolean = false,
    val phone: String ="",
    val status: String="",
    val username: String="",
    val vk: String=""
)