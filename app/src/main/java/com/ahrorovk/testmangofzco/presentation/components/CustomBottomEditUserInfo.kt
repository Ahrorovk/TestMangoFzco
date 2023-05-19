package com.ahrorovk.testmangofzco.presentation.components

import android.view.ViewTreeObserver
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileEvent
import kotlinx.coroutines.launch
import java.sql.Date


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomBottomEditUserInfo(
    value:String,
    onValueChange:(String)->Unit,
    onClick:()->Unit,
    placeholder: String,
    modifier: Modifier,
    date:String,
    typeOfItem:Boolean =false,
    onDatePick:(Long)->Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
        ) {
            ModalSheetDefaultStick()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (typeOfItem) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Birthday",
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        DatePickerLabel(date = date) {
                            onDatePick(it)
                        }
                    }
                }
                else
                {
                    CustomTextField(
                        value = value,
                        onValueChange = {
                            onValueChange(it)
                        },
                        hint = placeholder
                    )
                }
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.secondary,
                    onClick = {
                        onClick()
                    },
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                }
            }
        }
    }
}