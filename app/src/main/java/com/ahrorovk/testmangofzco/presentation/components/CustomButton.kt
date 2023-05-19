package com.ahrorovk.testmangofzco.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onClick:()->Unit,
    enabled:Boolean,
    isLoading:Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp, vertical = 50.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onSecondary),
            onClick = onClick,
            enabled = enabled
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else
                Text(text = "OK")
        }
    }
}