package com.example.weather.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.data.DataOrException
import com.example.weather.model.Weather
import com.example.weather.model.WeatherItem
import com.example.weather.navigation.WeatherScreens
import com.example.weather.screens.settings.SettingsViewModel
import com.example.weather.utils.formatDate
import com.example.weather.utils.formatDecimals
import com.example.weather.widgets.HumidityWindPressureRow
import com.example.weather.widgets.SunsetSunRiseRow
import com.example.weather.widgets.WeatherAppBar
import com.example.weather.widgets.WeatherDetailsRow
import com.example.weather.widgets.WeatherStateImage


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
   val  curCity: String = if (city!!.isBlank()) "Johannesburg" +
           "" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()){

        unit = unitFromDb[0].unit.split("")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {

            value = mainViewModel.getWeatherData(city = curCity,
            units = unit)

        }.value
        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null){
            MainScaffold(weather = weatherData.data!!, navController, isImperial = isImperial )
        }

    }



}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
                      WeatherAppBar(title = weather.city.name + " ,${weather.city.country}",
                          navController = navController,
                          onAddActionClicked = {
                                               navController.navigate(WeatherScreens.SearchScreen.name)
                          },
                          elevation = 5.dp){

                      }
    },
        content = { PaddingValues ->
            Column(modifier = Modifier.padding(PaddingValues)) {
                MainContent(data = weather, isImperial = isImperial)
            }
        }
    )
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    // val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    val weatherItem = data.list[0]

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(data.list[0].dt), // Wed Nov 30
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(6.dp)
        )

    }


    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), // Take up the entire available width
        contentAlignment = Alignment.Center
    )
    {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFBF00),
        )
        {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                val imageUrl =
                    "https://openweathermap.org/img/w/${data.list[0].weather[0].icon}.png"


                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(data.list[0].temp.day) + "°",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = data.list[0].weather[0].main, fontStyle = FontStyle.Italic)


            }


        }
    }

    HumidityWindPressureRow(weather = data.list[0], isImperial = isImperial)
    Divider()
    SunsetSunRiseRow(weather = data.list[0])
    Text(
        text = "Text Week",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,

        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEf1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
              LazyColumn(modifier = Modifier.padding(2.dp),
                  contentPadding = PaddingValues(1.dp)
              ){
                  items(items = data.list){ item: WeatherItem ->
                      WeatherDetailsRow(weather = item)
                      
                  }
              }
        }
}




// val imageUrl =  "https://openweathermap.org/img/w/${weather.weather[0]}.png"