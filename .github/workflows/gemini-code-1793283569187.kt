package com.lifepilot.personal.data.local.dao

import androidx.room.*
import com.lifepilot.personal.data.local.entities.RoutineActivityEntity
import com.lifepilot.personal.data.local.entities.RoutineLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine_activities ORDER BY startTime ASC")
    fun getAllActivities(): Flow<List<RoutineActivityEntity>>

    @Query("SELECT * FROM routine_activities WHERE dayOfWeek = :dayOfWeek OR dayOfWeek IS NULL ORDER BY startTime ASC")
    fun getActivitiesForDay(dayOfWeek: Int): Flow<List<RoutineActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: RoutineActivityEntity): Long

    @Delete
    suspend fun deleteActivity(activity: RoutineActivityEntity)

    @Query("SELECT * FROM routine_logs WHERE date = :date")
    fun getLogsForDate(date: LocalDate): Flow<List<RoutineLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLog(log: RoutineLogEntity): Long
}