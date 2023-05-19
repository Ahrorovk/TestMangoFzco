package com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen

import android.util.Log
import android.widget.Toast
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
import com.ahrorovk.testmangofzco.presentation.AuthorizationScreen.AuthorizationEvent
import com.ahrorovk.testmangofzco.presentation.components.CustomButton
import com.ahrorovk.testmangofzco.presentation.components.CustomTextField

@Composable
fun AuthCodeCheckScreen(
    state:AuthCodeCheckState,
    onEvent: (AuthCodeCheckEvent)->Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    state.checkAuthCodeState.response?.let {
    LaunchedEffect(key1 = true){
            Log.e("Response","$it")
            if(it.is_user_exists)
            {
                onEvent(AuthCodeCheckEvent.GoToProfile)
            }
            else {
                onEvent(AuthCodeCheckEvent.GoToRegistration)
                Toast.makeText(context,"You haven't account.Please register", Toast.LENGTH_LONG).show()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Verification", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            Text(
                text = "Please print code\n which we sent to ${state.phone}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(vertical = 45.dp))

            CustomTextField(value = state.code, hint = "Write Code", onValueChange = {
                val newString: String = it.removeSuffix("\n")
                if (newString.length < 7) {
                    onEvent(AuthCodeCheckEvent.OnCodeChange(newString))
                }
            })
            CustomButton(onClick = {
                onEvent(AuthCodeCheckEvent.CheckAuthCode)
            }, enabled = state.code.length == 6, isLoading = state.checkAuthCodeState.isLoading)
        }
    }
}