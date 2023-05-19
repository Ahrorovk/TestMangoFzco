package com.ahrorovk.testmangofzco.presentation.RegistrationScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.core.checkPhone
import com.ahrorovk.testmangofzco.core.checkUserName
import com.ahrorovk.testmangofzco.presentation.components.CustomButton
import com.ahrorovk.testmangofzco.presentation.components.CustomTextField
import com.ahrorovk.testmangofzco.presentation.components.PhoneNumberItem
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RegistrationScreen(
    state:RegistrationState,
    onEvent: (RegistrationEvent)->Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = state.userRegState.response!=null){
        state.userRegState.response?.let {
            if(it.user_id>0)
            {
                onEvent(RegistrationEvent.GoToProfile)
                Log.e("Response","$it")
            }
        }
    }
    scope.launch {
        if(state.error.isNotEmpty()){
            Toast.makeText(context, state.error,Toast.LENGTH_LONG).show()
            onEvent(RegistrationEvent.OnErrorChange(""))
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Registration", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(vertical = 45.dp))
            PhoneNumberItem(
                modifier = Modifier,
                suggestions = state.suggestionsItems,
                selectedText = state.selectedNumberRegionOfPhone,
                onRegionClick = { number, image ->
                    onEvent(RegistrationEvent.OnSelectedNumberRegionOfPhoneChange(number))
                    onEvent(RegistrationEvent.OnSelectedImageRegionOfPhoneChange(image))
                },
                value = state.phone,
                onValueChange = {
                    onEvent(RegistrationEvent.OnPhoneChange(it))
                },
                hint = "Phone Number",
                selectedImage = state.selectedImageRegionOfPhone
            )
            CustomTextField(value = state.name, onValueChange = {
                onEvent(RegistrationEvent.OnNameChange(it))
            }, hint = "Name")
            CustomTextField(value = state.userName, onValueChange = {
                onEvent(RegistrationEvent.OnUserNameChange(it))
            }, hint = "Username")
            CustomButton(
                    onClick = {
                        if(!checkPhone(state.phone)) {
                            onEvent(RegistrationEvent.OnErrorChange("Phone can only consist of numbers"))
                        }
                        if(!checkUserName(state.userName))
                        {
                            onEvent(RegistrationEvent.OnErrorChange("You can't use symbols besides '-' or '_'"))
                        }
                        if(checkPhone(state.phone) && checkUserName(state.userName)) {
                            onEvent(RegistrationEvent.SendUserRegistration)
                        }
                    },
                    enabled = state.userName.length >= 5 && state.name.isNotEmpty() && state.phone.length >= 9,
                    isLoading = state.userRegState.isLoading
                )
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp), contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "Do you have an account? Log in",
            modifier = Modifier.clickable { onEvent(RegistrationEvent.GoToAuthorization) })
    }
}
