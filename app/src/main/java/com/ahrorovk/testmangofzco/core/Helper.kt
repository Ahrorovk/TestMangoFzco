package com.ahrorovk.testmangofzco.core

import android.os.Build
import androidx.annotation.RequiresApi
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileEvent
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

fun checkPhone(phoneNumber:String):Boolean {
    for (i in phoneNumber.indices) {
        if (phoneNumber[i] < '0' || phoneNumber[i] > '9') {
            return false
        }
    }
    return true
}
fun checkUserName(userName:String):Boolean {
    for (i in userName.indices) {
        if ((userName[i] in 'a'..'z') || (userName[i] in 'A'..'Z') || (userName[i] in '0'..'9') || userName[i] == '-' || userName[i] == '_') {
            continue
        } else {
            return false
        }
    }
    return true
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toCurrentInMillis(): Long {
    val zonedDateTime = this.atStartOfDay(ZoneId.systemDefault())
    return zonedDateTime.toInstant().toEpochMilli()
}
fun zodiacMonth(s:String):String {
    val bd = s.split('-').map { it.toInt() }
    val mm = bd[1]
    val dd = bd[2]
    val zodiac = if ((dd >= 21 && mm == 3) || (mm == 4 && dd <= 20)) "Aries"
    else if ((dd >= 21 && mm == 4) || (mm == 5 && dd <= 20)) "Calf"
    else if ((dd >= 21 && mm == 5) || (mm == 6 && dd <= 21)) "Twins"
    else if ((dd >= 22 && mm == 6) || (mm == 7 && dd <= 22)) "Cancer"
    else if ((dd >= 23 && mm == 7) || (mm == 8 && dd <= 22)) "Leo"
    else if ((dd >= 23 && mm == 8) || (mm == 9 && dd <= 23)) "Virgo"
    else if ((dd >= 24 && mm == 9) || (mm == 10 && dd <= 23)) "Libra"
    else if ((dd >= 24 && mm == 10) || (mm == 11 && dd <= 22)) "Scorpio"
    else if ((dd >= 23 && mm == 11) || (mm == 12 && dd <= 21)) "Sagittarius"
    else if ((dd >= 22 && mm == 12) || (mm == 1 && dd <= 20)) "Capricorn"
    else if ((dd >= 21 && mm == 1) || (mm == 2 && dd <= 18)) "Aquarius"
    else "Pisces"
    return zodiac
}

fun checkUpdatesInfo(typeOfItem:String,type:String,itemState:String,state:String):String {
    return if (typeOfItem == type) itemState else state
}

fun onInfoStateChange(_state:ProfileState,event: ProfileEvent.OnInfoStatesChange,dataStoreManager: DataStoreManager,viewModelScope:CoroutineScope){
    if (_state.name != event.name) {
        viewModelScope.launch(Dispatchers.IO) {
            event.name?.let { dataStoreManager.updateName(it) }
        }
    }
    if (_state.username != event.username) {
        viewModelScope.launch(Dispatchers.IO) {
            event.username?.let { dataStoreManager.updateUsername(it) }
        }
    }

        viewModelScope.launch(Dispatchers.IO) {
            event.avatar.let { dataStoreManager.updateAvatarFilename(it.avatar) }
    }
    if (_state.birthday != event.birthday) {
        viewModelScope.launch(Dispatchers.IO) {
            event.birthday?.let { dataStoreManager.updateBirthday(it) }
        }
    }
    if (_state.city != event.city) {
        viewModelScope.launch(Dispatchers.IO) {
            event.city?.let { dataStoreManager.updateCity(it) }
        }
    }
    if (_state.instagram != event.instagram) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.updateInstagram(event.instagram ?: "")
        }
    }
    if (_state.status != event.status) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.updateStatus(event.status ?: "")
        }
    }
    if (_state.vk != event.vk) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.updateVK(event.vk ?: "")
        }
    }
}
