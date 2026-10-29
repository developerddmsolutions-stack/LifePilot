package com.lifepilot.personal.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val displayName: String,
    val age: Int?,
    val heightCm: Float?,
    val weightKg: Float?,
    val fitnessGoal: String, // MUSCLE_GAIN, FAT_LOSS, STRENGTH, ENDURANCE, MAINTENANCE
    val fitnessExperience: String, // BEGINNER, INTERMEDIATE, ADVANCED
    val workoutLocationPreference: String, // GYM, HOME, BOTH
    val preferredWakeTime: LocalTime?,
    val preferredSleepTime: LocalTime?,
    val dailyCalorieTarget: Int?,
    val dailyProteinTargetGrams: Int?,
    val dailyWaterTargetMl: Int = 2500,
    val isOnboardingCompleted: Boolean = false
)

@Entity(
    tableName = "routine_activities",
    indices = [Index("dayOfWeek"), Index("startTime")]
)
data class RoutineActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String, // MORNING, PRAYER, WORK, EXERCISE, MEAL, STUDY, LEISURE, SLEEP
    val startTime: LocalTime,
    val endTime: LocalTime,
    val dayOfWeek: Int?, // 1 (Mon) to 7 (Sun), null = all days
    val priority: Int = 1, // 1: Low, 2: Medium, 3: High
    val isRecurring: Boolean = true,
    val reminderMinutesBefore: Int? = 10,
    val notes: String? = null
)

@Entity(
    tableName = "routine_logs",
    foreignKeys = [
        ForeignKey(
            entity = RoutineActivityEntity::class,
            parentColumns = ["id"],
            childColumns = ["activityId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("date"), Index("activityId")]
)
data class RoutineLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val activityId: Long?,
    val date: LocalDate,
    val status: String, // COMPLETED, SKIPPED, RESCHEDULED
    val actualStart: LocalTime?,
    val actualEnd: LocalTime?,
    val notes: String? = null
)

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val targetDaysPerWeek: Int = 7,
    val reminderTime: LocalTime? = null,
    val isArchived: Boolean = false
)

@Entity(
    tableName = "habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habitId", "date"], unique = true)]
)
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val habitId: Long,
    val date: LocalDate,
    val isCompleted: Boolean,
    val notes: String? = null
)

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetMuscle: String, // CHEST, BACK, LEGS, SHOULDERS, ARMS, CORE
    val secondaryMuscles: String = "", // Comma-separated
    val equipment: String, // BARBELL, DUMBBELL, MACHINE, BODYWEIGHT, BANDS
    val isHomeFriendly: Boolean,
    val instructions: String,
    val defaultRestSeconds: Int = 90
)

@Entity(tableName = "workout_sessions", indices = [Index("date")])
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val locationType: String, // GYM, HOME
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime?,
    val notes: String? = null,
    val isCompleted: Boolean = false
)

@Entity(
    tableName = "workout_sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sessionId"), Index("exerciseId")]
)
data class WorkoutSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val exerciseId: Long,
    val setNumber: Int,
    val weightKg: Float,
    val repetitions: Int,
    val rpe: Float? = null,
    val isWarmup: Boolean = false,
    val isCompleted: Boolean = true
)

@Entity(tableName = "nutrition_logs", indices = [Index("date")])
data class NutritionLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val mealType: String, // BREAKFAST, LUNCH, DINNER, SNACK, PRE_WORKOUT, POST_WORKOUT
    val foodName: String,
    val servingAmount: Float,
    val servingUnit: String,
    val calories: Int,
    val proteinGrams: Float,
    val carbGrams: Float,
    val fatGrams: Float
)

@Entity(
    tableName = "hydration_logs",
    indices = [Index("date", unique = true)]
)
data class HydrationLogEntity(
    @PrimaryKey val date: LocalDate,
    val amountMl: Int
)

@Entity(
    tableName = "wellness_logs",
    indices = [Index("date", unique = true)]
)
data class WellnessLogEntity(
    @PrimaryKey val date: LocalDate,
    val sleepDurationHours: Float?,
    val sleepBedTime: LocalTime?,
    val sleepWakeTime: LocalTime?,
    val moodRating: Int?, // 1 to 5
    val energyRating: Int?, // 1 to 5
    val sorenessRating: Int?, // 1 to 5
    val notes: String? = null
)