package com.ahrorovk.testmangofzco.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun PhoneNumberItem(
    modifier: Modifier,
    suggestions: List<Pair<Int,Int>>,
    selectedText: Int,
    selectedImage: Int,
    onRegionClick:(Int,Int)->Unit,
    value: String,
    onValueChange:(String)->Unit,
    hint:String
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    Row(modifier) {
        CustomTextFieldCountryCodeNumberPicker(
            modifier = Modifier
                .height(60.dp)
                .weight(2f)
                .background(Color(0x00FFFFFF))
                .padding(start = 36.dp, end = 8.dp)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            currentText = selectedText.toString(),
            suggestions = suggestions,
            onClick = onRegionClick,
            selectedImage = selectedImage
        )
        Spacer(modifier = Modifier.width(5.dp))
        CustomTextField(
            modifier = Modifier
                .weight(3f)
                .height(with(LocalDensity.current) { textFieldSize.height.toDp() })
                .padding(end = 36.dp),
            hint = hint,
            value = value,
            onValueChange = {
                val newString: String = it.removeSuffix("\n")
                if (newString.length < 14) {
                    onValueChange(newString)
                }
            },
            isNumberOption = true
        )
    }
}