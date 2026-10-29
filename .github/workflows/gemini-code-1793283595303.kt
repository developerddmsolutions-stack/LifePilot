package com.lifepilot.personal.domain.export

import com.lifepilot.personal.data.local.entities.NutritionLogEntity
import com.lifepilot.personal.data.local.entities.RoutineActivityEntity
import com.lifepilot.personal.data.local.entities.UserProfileEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

@Serializable
data class LifePilotBackupPayload(
    val exportDate: String,
    val profileName: String,
    val version: Int = 1,
    val mealsCount: Int,
    val activitiesCount: Int
)

class ExportManager {

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    fun exportToJson(
        profile: UserProfileEntity?,
        meals: List<NutritionLogEntity>,
        routines: List<RoutineActivityEntity>
    ): String {
        val payload = LifePilotBackupPayload(
            exportDate = LocalDate.now().toString(),
            profileName = profile?.displayName ?: "Personal User",
            mealsCount = meals.size,
            activitiesCount = routines.size
        )
        return json.encodeToString(payload)
    }

    fun generateDietitianReport(
        startDate: LocalDate,
        endDate: LocalDate,
        meals: List<NutritionLogEntity>,
        avgWaterMl: Int
    ): String {
        val daysCount = (endDate.toEpochDay() - startDate.toEpochDay() + 1).coerceAtLeast(1)
        val totalCalories = meals.sumOf { it.calories }
        val totalProtein = meals.sumOf { it.proteinGrams.toDouble() }
        val totalCarbs = meals.sumOf { it.carbGrams.toDouble() }
        val totalFat = meals.sumOf { it.fatGrams.toDouble() }

        return buildString {
            appendLine("==============================================")
            appendLine("   LIFEPILOT NUTRITION SUMMARY FOR CLINICIAN  ")
            appendLine("==============================================")
            appendLine("Report Window: $startDate to $endDate ($daysCount days)")
            appendLine("Average Daily Intake:")
            appendLine(" - Energy:  ${totalCalories / daysCount} kcal/day")
            appendLine(" - Protein: ${"%.1f".format(totalProtein / daysCount)} g/day")
            appendLine(" - Carbs:   ${"%.1f".format(totalCarbs / daysCount)} g/day")
            appendLine(" - Fats:    ${"%.1f".format(totalFat / daysCount)} g/day")
            appendLine(" - Water:   ~$avgWaterMl mL/day")
            appendLine("\nLog Entries (${meals.size} items recorded):")
            meals.groupBy { it.date }.forEach { (date, dailyMeals) ->
                appendLine("\n[$date]")
                dailyMeals.forEach { m ->
                    appendLine("  * [${m.mealType}] ${m.foodName} (${m.servingAmount} ${m.servingUnit}) - ${m.calories} kcal | P: ${m.proteinGrams}g | C: ${m.carbGrams}g | F: ${m.fatGrams}g")
                }
            }
            appendLine("\nNote: Generated offline by LifePilot. Data is self-reported.")
        }
    }
}