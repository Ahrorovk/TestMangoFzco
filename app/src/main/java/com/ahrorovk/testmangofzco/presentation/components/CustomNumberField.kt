package com.ahrorovk.testmangofzco.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomNumberField(
    modifier: Modifier = Modifier,
    value:String,
    hint: String,
    onValueChange:(String)->Unit,
) {
    TextField(
        modifier = modifier,
        maxLines = 1,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = hint)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colors.onBackground,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}