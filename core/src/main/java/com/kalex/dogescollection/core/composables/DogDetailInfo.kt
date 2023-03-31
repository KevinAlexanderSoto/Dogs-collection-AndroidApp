package com.kalex.dogescollection.core.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalex.dogescollection.core.values.boldText
import com.kalex.dogescollection.core.values.contentText

@Composable
fun DogDetailInfo(
    detailsTitle: String,
    heightValue: String,
    heightTitle: String,
    weightValue: String,
    weightTitle: String,
) {
    Column(
        modifier = Modifier.padding(8.dp),
    ) {
        Text(text = detailsTitle, fontSize = 16.sp)
        Text(text = weightValue, style = boldText)
        Text(text = weightTitle, style = contentText)
        Text(text = heightValue, style = boldText)
        Text(text = heightTitle, style = contentText)
    }
}
