package com.ahrorovk.testmangofzco.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileItem(
    title:String,
    text:String,
    isLoading:Boolean,
    onClick:()->Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .clickable { onClick() }) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp).fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$title : ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Text(text = text, maxLines = 1)
            }
            if (isLoading) {
                CircularProgressIndicator()
            } else
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = null
                )
        }
        Divider()
    }
}