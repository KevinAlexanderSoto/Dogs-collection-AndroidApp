package com.kalex.dogescollection.core.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DogDetailItem(
    detailsTitle: String,
) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .padding(24.dp), // intrinsic measurements
        horizontalArrangement = Arrangement.Center,
    ) {
        Divider(
            color = Color.Red,
            modifier = Modifier
                .width(1.dp).height(54.dp),
        )

        Text(
            text = detailsTitle,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp, 1.dp),
        )

        Divider(
            color = Color.Red,
            modifier = Modifier
                .fillMaxHeight() // fill the max height
                .width(1.dp),
        )
    }
}
