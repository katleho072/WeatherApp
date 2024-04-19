package com.example.weather.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.model.Favourite
import com.example.weather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: WeatherDbRepository )
    : ViewModel(){
        private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
        val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavourites().distinctUntilChanged()
                .collect { listOfFavs ->
                    if (listOfFavs.isNullOrEmpty()) {

                    } else {
                        _favList.value = listOfFavs
                    }

                }

        }
    }
    fun deleteFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.deleteFavourite(favourite)
        }}
    fun insertFavourite(favourite: Favourite){
         viewModelScope.launch {
            repository.insertFavourite(favourite)
        }

    }

        fun updateFavorite(favourite: Favourite) {
            viewModelScope.launch {
                repository.updateFavourite(favourite)}}}














