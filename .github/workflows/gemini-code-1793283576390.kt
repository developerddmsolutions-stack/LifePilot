package com.lifepilot.personal.data.local.dao

import androidx.room.*
import com.lifepilot.personal.data.local.entities.ExerciseEntity
import com.lifepilot.personal.data.local.entities.WorkoutSessionEntity
import com.lifepilot.personal.data.local.entities.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

data class ExerciseWithSets(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val sets: List<WorkoutSetEntity>
)

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE isHomeFriendly = 1 ORDER BY name ASC")
    fun getHomeExercises(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialExercises(exercises: List<ExerciseEntity>)

    @Insert
    suspend fun insertSession(session: WorkoutSessionEntity): Long

    @Update
    suspend fun updateSession(session: WorkoutSessionEntity)

    @Query("SELECT * FROM workout_sessions WHERE date = :date ORDER BY startTime DESC")
    fun getSessionsForDate(date: LocalDate): Flow<List<WorkoutSessionEntity>>

    @Insert
    suspend fun insertSet(workoutSet: WorkoutSetEntity)

    @Query("SELECT * FROM workout_sets WHERE sessionId = :sessionId ORDER BY setNumber ASC")
    fun getSetsForSession(sessionId: Long): Flow<List<WorkoutSetEntity>>

    @Query("""
        SELECT MAX(weightKg) FROM workout_sets 
        WHERE exerciseId = :exerciseId AND isCompleted = 1
    """)
    suspend fun getPersonalRecordWeight(exerciseId: Long): Float?

    @Query("""
        SELECT ws.* FROM workout_sets ws
        INNER JOIN workout_sessions s ON ws.sessionId = s.id
        WHERE ws.exerciseId = :exerciseId AND s.date < :currentDate AND ws.isCompleted = 1
        ORDER BY s.date DESC, ws.setNumber ASC LIMIT 5
    """)
    suspend fun getPreviousPerformance(exerciseId: Long, currentDate: LocalDate): List<WorkoutSetEntity>
}