package com.example.weather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather.model.Favourite
import com.example.weather.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favourite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavBid(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourites(favourite: Favourite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavourites()

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)



    // Unit table
    @Query("SELECT * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)



}

/*
@Query("SELECT * from settings_tbl")
fun getUnits(): Flow<List<Unit>>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertUnit(unit: Unit)

@Update(onConflict = OnConflictStrategy.REPLACE)
suspend fun updateUnit(unit: Unit)

@Query("DELETE from settings_tbl")
suspend fun deleteAllUnits(unit: Unit)

@Delete
suspend fun deleteUnit(unit: Unit)
*/
