package com.ahrorovk.testmangofzco.presentation.AuthCodeCheckScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.testmangofzco.presentation.components.CustomButton
import com.ahrorovk.testmangofzco.presentation.components.CustomNumberField

@Composable
fun AuthCodeCheckScreen(
    state:AuthCodeCheckState,
    onEvent: (AuthCodeCheckEvent)->Unit
) {
    val context = LocalContext.current
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

            CustomNumberField(value = state.code, hint = "Write Code", onValueChange = {
                val newString: String = it.removeSuffix("\n")
                if (newString.length < 7) {
                    onEvent(AuthCodeCheckEvent.OnCodeChange(newString))
                }
            })
            CustomButton(onClick = {
                if(state.code!="133337")
                    Toast.makeText(context,"Wrong identify code!",Toast.LENGTH_LONG).show()
                else
                    onEvent(AuthCodeCheckEvent.CheckAuthCode)
            }, enabled = state.code.length == 6, isLoading = state.checkAuthCodeState.isLoading)
        }
    }
}