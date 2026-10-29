package com.lifepilot.personal.data.local.dao

import androidx.room.*
import com.lifepilot.personal.data.local.entities.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface NutritionDao {
    @Query("SELECT * FROM nutrition_logs WHERE date = :date ORDER BY id ASC")
    fun getMealsForDate(date: LocalDate): Flow<List<NutritionLogEntity>>

    @Insert
    suspend fun insertMeal(meal: NutritionLogEntity)

    @Delete
    suspend fun deleteMeal(meal: NutritionLogEntity)

    @Query("SELECT * FROM hydration_logs WHERE date = :date")
    fun getHydration(date: LocalDate): Flow<HydrationLogEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHydration(log: HydrationLogEntity)
}

@Dao
interface WellnessDao {
    @Query("SELECT * FROM wellness_logs WHERE date = :date")
    fun getWellnessForDate(date: LocalDate): Flow<WellnessLogEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWellness(log: WellnessLogEntity)

    @Query("SELECT * FROM wellness_logs WHERE date BETWEEN :start AND :end ORDER BY date ASC")
    suspend fun getWellnessBetween(start: LocalDate, end: LocalDate): List<WellnessLogEntity>
}