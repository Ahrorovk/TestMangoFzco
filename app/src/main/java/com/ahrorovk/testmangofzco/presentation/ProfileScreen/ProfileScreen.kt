package com.ahrorovk.testmangofzco.presentation.ProfileScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahrorovk.testmangofzco.core.states.RefreshTokenState
import com.ahrorovk.testmangofzco.core.states.UpdateUserInfoState
import com.ahrorovk.testmangofzco.core.zodiacMonth
import com.ahrorovk.testmangofzco.presentation.components.CustomBottomEditUserInfo
import com.ahrorovk.testmangofzco.presentation.components.DatePickerLabel
import com.ahrorovk.testmangofzco.presentation.components.ProfileItem
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent)->Unit
) {
    val sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val currentTime = System.currentTimeMillis()
    val tenMinutesAgo = currentTime / 60000
    val accessTokenLife = state.accessTokenLife / 60000
    val context = LocalContext.current

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {

            CustomBottomEditUserInfo(
                value = state.itemState,
                onValueChange = {
                    onEvent(ProfileEvent.OnItemChange(it))
                },
                onClick = {
                    scope.launch {
                        sheetState.animateTo(ModalBottomSheetValue.Hidden)
                    }
                    if(tenMinutesAgo - accessTokenLife > (9) && accessTokenLife>0) {
                        onEvent(ProfileEvent.RefreshToken)
                    }
                    else if(tenMinutesAgo - accessTokenLife <= (9)) {
                        onEvent(ProfileEvent.ChangeItem)

                    }
                },
                placeholder = "Change",
                modifier = Modifier,
                typeOfItem = state.typeOfItem == "birthday",
                onDatePick = {
                    onEvent(ProfileEvent.OnItemChange(Date(it).toString()))
                }, date = state.itemState
            )
        }) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile")
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context,"You have logged out of account",Toast.LENGTH_LONG).show()
                        onEvent(ProfileEvent.OnLogOutFromAccount("", "", 0))
                        onEvent(ProfileEvent.LogOutFromAccount)
                    }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        modifier = Modifier
                            .size(180.dp)
                            .padding(vertical = 10.dp),
                        painter = rememberAsyncImagePainter(state.avatar),
                        contentDescription = null
                    )
                    ProfileItem(
                        title = "Avatar",
                        text = state.avatar,
                        isLoading = state.typeOfItem == "avatar" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("avatar"))
                        onEvent(ProfileEvent.OnItemChange(state.avatar))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                    ProfileItem(
                        title = "Username",
                        text = state.username,
                        isLoading = "".toBoolean()
                    ) {
                    }
                    ProfileItem(
                        title = "Name",
                        text = state.name,
                        isLoading = state.typeOfItem == "name" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("name"))
                        onEvent(ProfileEvent.OnItemChange(state.name))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                    ProfileItem(
                        title = "City",
                        text = state.city,
                        state.typeOfItem == "city" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("city"))
                        onEvent(ProfileEvent.OnItemChange(state.city))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                    ProfileItem(
                        title = "Birthday",
                        text = state.birthday,
                        state.typeOfItem == "birthday" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("birthday"))
                        onEvent(ProfileEvent.OnItemChange(state.birthday))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                    ProfileItem(
                        title = "Zodiac sign",
                        text = zodiacMonth(state.birthday),
                        state.typeOfItem == "zodiacMonth" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                    }
                    ProfileItem(title = "Phone", text = state.phone, false) {
                    }
                    ProfileItem(
                        title = "Status",
                        text = state.status,
                        state.typeOfItem == "status" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("status"))
                        onEvent(ProfileEvent.OnItemChange(state.status))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                    ProfileItem(
                        title = "VK",
                        text = state.vk,
                        state.typeOfItem == "vk" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("vk"))
                        onEvent(ProfileEvent.OnItemChange(state.vk))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                    ProfileItem(
                        title = "Instagram",
                        text = state.instagram,
                        state.typeOfItem == "instagram" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading)
                    ) {
                        onEvent(ProfileEvent.GoToChange("instagram"))
                        onEvent(ProfileEvent.OnItemChange(state.instagram))
                        scope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                }
            }
        }
    }
}