package com.ahrorovk.testmangofzco.presentation.ProfileScreen

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ahrorovk.testmangofzco.core.zodiacMonth
import com.ahrorovk.testmangofzco.presentation.components.CustomBottomEditUserInfo
import com.ahrorovk.testmangofzco.presentation.components.ProfileItem
import kotlinx.coroutines.launch
import java.sql.Date
import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.rememberImagePainter
import com.ahrorovk.testmangofzco.core.Constants.TEST_URL

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
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onEvent(
                ProfileEvent.OnAvatarBitmapChange(
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        uri
                    )
                )
            )
        }
    }
    // Проверка разрешения на чтение хранилища
    val hasReadStoragePermission = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
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
                        if (state.avatarBitmap != null) {
                            Image(
                                bitmap = state.avatarBitmap.asImageBitmap(),
                                contentDescription = "Avatar",
                                modifier = Modifier.size(200.dp)
                            )
                        } else {
                            Image(
                                painter = rememberImagePainter(data = TEST_URL+"/"+state.avatar),
                                contentDescription = "Avatar",
                                modifier = Modifier.width(150.dp).height(250.dp),
                            )
                        }

                        Button(onClick = {
                            if (hasReadStoragePermission) {
                                galleryLauncher.launch("image/*")
                                Log.e("Launch","Success")
                            }
                                else {
                                ActivityCompat.requestPermissions(
                                    context as Activity,
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    123
                                )
                                Log.e("Launch","Error")
                            }
                        }) {
                            Text(text = "Choose Photo")
                        }

                        Button(
                            onClick = {
                                onEvent(ProfileEvent.GoToChange("avatar"))
                                onEvent(ProfileEvent.OnUploadAvatar)
                                onEvent(ProfileEvent.OnAvatarBitmapChange(null))
                                      },
                            enabled = state.avatarBitmap != null
                        ) {
                            if(state.typeOfItem == "avatar" && (state.currentUserState.isLoading || state.updateUserInfoState.isLoading))
                                CircularProgressIndicator()
                            else
                                Text(text = "Send Avatar")
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
                        text = if(state.birthday!="")zodiacMonth(state.birthday) else "",
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