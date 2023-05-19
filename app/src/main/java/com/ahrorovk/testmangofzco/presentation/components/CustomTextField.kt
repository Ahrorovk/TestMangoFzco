package com.ahrorovk.testmangofzco.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value:String,
    hint: String,
    onValueChange:(String)->Unit,
    isNumberOption:Boolean = false
) {
    if (isNumberOption) {
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
    } else {
        TextField(
            modifier = modifier,
            value = value,
            singleLine = true,
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
            )
        )
    }

}