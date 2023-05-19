package com.ahrorovk.testmangofzco.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun CustomTextFieldCountryCodeNumberPicker(
    modifier: Modifier,
    currentText: String,
    selectedImage:Int,
    suggestions: List<Pair<Int,Int>>,
    onClick: (Int,Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Surface(
        modifier = modifier
            .clickable {
                expanded = true
            }
            .onGloballyPositioned { coordinates ->
                textfieldSize = coordinates.size.toSize()
            },
        elevation = 0.dp, shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(id = selectedImage),
                        contentDescription = null
                    )

                    Text(text = "+$currentText")

                    Icon(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        imageVector = icon,
                        contentDescription = "visibility",
                        tint = Color.Gray
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() }),
            ) {
                suggestions.forEach { label ->

                    DropdownMenuItem(
                        onClick = {
                            onClick(label.first, label.second)
                            expanded = false
                        }) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier.width(50.dp).height(50.dp),
                                painter = painterResource(id = label.second),
                                contentDescription = null
                            )
                            Text(text = "+${label.first}", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}