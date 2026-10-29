package com.lifepilot.personal.data.local

import androidx.room.*
import com.lifepilot.personal.data.local.dao.*
import com.lifepilot.personal.data.local.entities.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.format(DateTimeFormatter.ISO_LOCAL_TIME)

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
}

@Database(
    entities = [
        UserProfileEntity::class,
        RoutineActivityEntity::class,
        RoutineLogEntity::class,
        HabitEntity::class,
        HabitLogEntity::class,
        ExerciseEntity::class,
        WorkoutSessionEntity::class,
        WorkoutSetEntity::class,
        NutritionLogEntity::class,
        HydrationLogEntity::class,
        WellnessLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class LifePilotDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun routineDao(): RoutineDao
    abstract fun habitDao(): HabitDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun nutritionDao(): NutritionDao
    abstract fun wellnessDao(): WellnessDao
}