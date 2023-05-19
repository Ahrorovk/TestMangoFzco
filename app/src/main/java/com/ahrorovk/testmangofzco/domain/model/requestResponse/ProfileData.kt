package com.ahrorovk.testmangofzco.domain.model.requestResponse

data class ProfileData(
    val avatar: String="avatar",
    val avatars: Avatars = Avatars("","",""),
    val birthday: String="birthday",
    val city: String="city",
    val completed_task: Int =0,
    val created: String="",
    val id: Int=0,
    val instagram: String="",
    val last: String="",
    val name: String="",
    val online: Boolean = false,
    val phone: String ="",
    val status: String="",
    val username: String="",
    val vk: String=""
)