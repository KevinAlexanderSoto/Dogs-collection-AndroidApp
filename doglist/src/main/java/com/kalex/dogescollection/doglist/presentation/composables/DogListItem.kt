package com.kalex.dogescollection.doglist.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.core.values.SecondaryContainer
import com.kalex.dogescollection.doglist.R

@Composable
fun DogListItem(
    dog: Dog,
    onItemClick: ((Dog) -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentWidth().padding(0.dp, 4.dp, 0.dp, 0.dp),
    ) {
        Text(
            text = dog.name_es,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp, 1.dp),
        )

        if (dog.inCollection) {
            CardInCollection(dog, onItemClick)
        } else {
            CardOutCollection()
        }
    }
}

@Composable
fun CardInCollection(
    dog: Dog,
    onItemClick: ((Dog) -> Unit)? = null,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .height(110.dp)
            .fillMaxWidth()
            .clickable { onItemClick?.invoke(dog) },
    ) {
        AsyncImage(
            model = dog.image_url,
            contentDescription = "Dog img",
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
fun CardOutCollection() {
    Card(
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .height(110.dp)
            .fillMaxWidth()
            .clickable { },
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().background(SecondaryContainer),
        ) {
            Image(
                painterResource(R.drawable.round_question_mark_24),
                contentDescription = "Dog img",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
