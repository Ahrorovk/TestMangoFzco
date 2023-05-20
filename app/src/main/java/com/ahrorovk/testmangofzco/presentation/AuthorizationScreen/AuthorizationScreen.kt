package com.ahrorovk.testmangofzco.presentation.AuthorizationScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.testmangofzco.presentation.components.CustomButton
import com.ahrorovk.testmangofzco.presentation.components.PhoneNumberItem

@Composable
fun AuthorizationScreen(
    state: AuthorizationState,
    onEvent: (AuthorizationEvent)->Unit
) {
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
            }, enabled = state.phone.length >= 9, isLoading = state.authCodeSendState.isLoading)
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