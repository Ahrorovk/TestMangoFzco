package com.ahrorovk.testmangofzco.presentation.AuthorizationScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileEvent
import com.ahrorovk.testmangofzco.presentation.RegistrationScreen.RegistrationEvent
import com.ahrorovk.testmangofzco.presentation.components.CustomButton
import com.ahrorovk.testmangofzco.presentation.components.PhoneNumberItem

@Composable
fun AuthorizationScreen(
    state: AuthorizationState,
    onEvent: (AuthorizationEvent)->Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = state.authCodeSendState.response!=null){
        state.authCodeSendState.response?.let {
            Log.e("Response","$it")
            if(it.is_success)
            {
                onEvent(AuthorizationEvent.GoToSecondStepOfAuth)
            }
            else {
                onEvent(AuthorizationEvent.GoToRegistration)
                Toast.makeText(context,"You haven't account.Please register",Toast.LENGTH_LONG).show()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Authorization", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(vertical = 45.dp))
            PhoneNumberItem(
                modifier = Modifier,
                suggestions = state.suggestionsItems,
                selectedText = state.selectedNumberRegionOfPhone,
                onRegionClick = { number, image ->
                    onEvent(AuthorizationEvent.OnSelectedNumberRegionOfPhoneChange(number))
                    onEvent(AuthorizationEvent.OnSelectedImageRegionOfPhoneChange(image))
                },
                value = state.phone,
                onValueChange = {
                    onEvent(AuthorizationEvent.OnPhoneChange(it))
                },
                hint = "Phone Number",
                selectedImage = state.selectedImageRegionOfPhone
            )
            CustomButton(onClick = {
                onEvent(AuthorizationEvent.SendAuthCode)
            }, enabled = state.phone.length>=9, isLoading = state.authCodeSendState.isLoading)
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp), contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "Don't you have an account? Sign up",
            modifier = Modifier.clickable { onEvent(AuthorizationEvent.GoToRegistration) })
    }
}