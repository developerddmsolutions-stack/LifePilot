package com.lifepilot.personal.domain.coach

import com.lifepilot.personal.data.local.entities.RoutineActivityEntity
import com.lifepilot.personal.data.local.entities.RoutineLogEntity
import com.lifepilot.personal.data.local.entities.WellnessLogEntity
import java.time.LocalDate

data class CoachRecommendation(
    val id: String,
    val title: String,
    val explanation: String,
    val severity: RecommendationSeverity,
    val actionType: CoachActionType
)

enum class RecommendationSeverity { INFO, NOTICE, WARNING }
enum class CoachActionType { SHORTEN_WORKOUT, SCHEDULE_REST, HYDRATION_CHECK, ROUTINE_SHIFT }

class SmartCoachEngine {

    fun evaluateDailyPlan(
        today: LocalDate,
        scheduledActivities: List<RoutineActivityEntity>,
        recentLogs: List<RoutineLogEntity>,
        wellnessHistory: List<WellnessLogEntity>,
        availableMinutes: Int
    ): List<CoachRecommendation> {
        val recommendations = mutableListOf<CoachRecommendation>()

        // Rule 1: High Soreness / Low Energy detected
        val yesterdayWellness = wellnessHistory.firstOrNull { it.date == today.minusDays(1) }
        if (yesterdayWellness != null) {
            val highSoreness = (yesterdayWellness.sorenessRating ?: 0) >= 4
            val lowEnergy = (yesterdayWellness.energyRating ?: 3) <= 2
            if (highSoreness && lowEnergy) {
                recommendations.add(
                    CoachRecommendation(
                        id = "rec_recovery_fatigue",
                        title = "Active Recovery Advised",
                        explanation = "Yesterday's logs show soreness level ${yesterdayWellness.sorenessRating}/5 and energy ${yesterdayWellness.energyRating}/5. Consider converting today's intense lift into mobility work or zone 2 walking.",
                        severity = RecommendationSeverity.WARNING,
                        actionType = CoachActionType.SCHEDULE_REST
                    )
                )
            }
        }

        // Rule 2: Time compression
        val scheduledWorkout = scheduledActivities.find { it.category == "EXERCISE" }
        if (scheduledWorkout != null && availableMinutes in 1..40) {
            recommendations.add(
                CoachRecommendation(
                    id = "rec_time_shorten",
                    title = "Time Crunch: 30-Minute Express Routine",
                    explanation = "You have only $availableMinutes minutes available today. Shift to antagonist supersets (e.g., Bench Press paired with Dumbbell Rows) to maintain volume while halving rest time.",
                    severity = RecommendationSeverity.NOTICE,
                    actionType = CoachActionType.SHORTEN_WORKOUT
                )
            )
        }

        // Rule 3: Repeatedly missed block pattern
        val missedCounts = recentLogs
            .filter { it.status == "SKIPPED" }
            .groupBy { it.activityId }

        for ((activityId, skips) in missedCounts) {
            if (skips.size >= 3) {
                val act = scheduledActivities.find { it.id == activityId }
                if (act != null) {
                    recommendations.add(
                        CoachRecommendation(
                            id = "rec_reschedule_${act.id}",
                            title = "Recurring Friction: ${act.title}",
                            explanation = "This activity was skipped ${skips.size} times over the last week. Consider moving it 30-60 minutes earlier or reducing its planned duration.",
                            severity = RecommendationSeverity.NOTICE,
                            actionType = CoachActionType.ROUTINE_SHIFT
                        )
                    )
                }
            }
        }

        return recommendations
    }
}