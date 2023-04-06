package com.kalex.dogescollection.doglist.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.doglist.presentation.ui.DogListFragmentDirections

@Composable
fun DogList(
    dogList: List<Dog>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(2.dp, 4.dp),
    ) {
        items(dogList.size) { dog ->
            DogListItem(dogList[dog]) {
                val bundle = DogListFragmentDirections.actionDogListFragmentToDogListDetailFragment(it)
                navController.navigate(bundle)
            }
        }
    }
}