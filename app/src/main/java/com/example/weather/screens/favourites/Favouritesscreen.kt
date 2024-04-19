package com.example.weather.screens.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.WeatherApp
import com.example.weather.model.Favourite
import com.example.weather.navigation.WeatherScreens
import com.example.weather.widgets.WeatherAppBar

@Composable
fun FavouriteScreen(navController: NavController,
                    favouriteViewModel: FavouriteViewModel = hiltViewModel()){
    Scaffold(topBar = {
       WeatherAppBar(
           title = "favorite Cities",
           icon = Icons.Default.ArrowBack,
           false,
           navController = navController){
           navController.popBackStack()
       }
    }, content = { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            Surface (modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()){
                Column (verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    val list = favouriteViewModel.favList.collectAsState().value
                    LazyColumn(){
                        items(items = list){
                            CityRow(it, navController = navController, favouriteViewModel)
                        }
                    }
                    
                }

            }


        }
    })

    
}

@Composable
fun CityRow(favourite: Favourite,
            navController: NavController,
            favouriteViewModel: FavouriteViewModel) {
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                 navController.navigate(WeatherScreens.MainScreen.name + "/${favourite.city}")
            }, shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFB2DFDB)
    ) {
        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text = favourite.city,
                modifier = Modifier.padding(start = 4.dp))
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(text = favourite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium)
            }
            Icon(imageVector = Icons.Rounded.Delete,
                contentDescription = "delete",
                modifier = Modifier.clickable {
                    favouriteViewModel.deleteFavourite(favourite)
                }, tint = Color.Red.copy(alpha = 0.3f))

        }

    }



}
