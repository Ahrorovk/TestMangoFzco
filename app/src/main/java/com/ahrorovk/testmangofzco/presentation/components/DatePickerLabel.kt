package com.ahrorovk.testmangofzco.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahrorovk.testmangofzco.core.toCurrentInMillis
import com.maxkeppeker.sheets.core.models.base.rememberSheetState

@SuppressLint("NewApi")
@Composable
fun DatePickerLabel(
    date: String,
    onDatePick: (Long) -> Unit,
) {
    val calendarState = rememberSheetState()

    Box(modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(Color.LightGray)
        .clickable {
            calendarState.show()
        }) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = Color.Gray
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = date
            )
            Icon(
                modifier = Modifier.padding(start = 6.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
    DatePicker(calendarState) {
        onDatePick(it.toCurrentInMillis())
    }
}