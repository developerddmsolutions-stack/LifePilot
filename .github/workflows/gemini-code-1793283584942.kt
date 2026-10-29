package com.lifepilot.personal.data.local

import com.lifepilot.personal.data.local.entities.ExerciseEntity

object InitialDataSeeder {
    fun getStarterExercises(): List<ExerciseEntity> = listOf(
        // Gym Exercises
        ExerciseEntity(
            name = "Barbell Bench Press",
            targetMuscle = "CHEST",
            secondaryMuscles = "Triceps, Anterior Deltoid",
            equipment = "BARBELL",
            isHomeFriendly = false,
            instructions = "Retract scapulae, maintain five points of contact, lower bar to mid-sternum with elbows tucked at 45 degrees, and drive upwards.",
            defaultRestSeconds = 120
        ),
        ExerciseEntity(
            name = "Barbell Back Squat",
            targetMuscle = "LEGS",
            secondaryMuscles = "Glutes, Core, Lower Back",
            equipment = "BARBELL",
            isHomeFriendly = false,
            instructions = "Brace core against belt, break at hips and knees simultaneously, achieve parallel depth, drive through mid-foot.",
            defaultRestSeconds = 180
        ),
        ExerciseEntity(
            name = "Conventional Deadlift",
            targetMuscle = "BACK",
            secondaryMuscles = "Hamstrings, Glutes, Forearms",
            equipment = "BARBELL",
            isHomeFriendly = false,
            instructions = "Set bar over mid-foot, hinge hips back, pull slack out of the bar, drive the floor away.",
            defaultRestSeconds = 180
        ),
        ExerciseEntity(
            name = "Seated Overhead Dumbbell Press",
            targetMuscle = "SHOULDERS",
            secondaryMuscles = "Triceps",
            equipment = "DUMBBELL",
            isHomeFriendly = true,
            instructions = "Press dumbbells upward without clanking, keeping ribs pinned and core braced.",
            defaultRestSeconds = 90
        ),
        // Home / Minimal Equipment Exercises
        ExerciseEntity(
            name = "Push-Up",
            targetMuscle = "CHEST",
            secondaryMuscles = "Triceps, Core",
            equipment = "BODYWEIGHT",
            isHomeFriendly = true,
            instructions = "Maintain rigid plank alignment from head to heels. Lower chest to 1 inch off floor.",
            defaultRestSeconds = 60
        ),
        ExerciseEntity(
            name = "Bulgarian Split Squat",
            targetMuscle = "LEGS",
            secondaryMuscles = "Glutes, Balance",
            equipment = "DUMBBELL",
            isHomeFriendly = true,
            instructions = "Elevate rear foot on chair or bench, descend until front thigh is parallel to ground.",
            defaultRestSeconds = 90
        ),
        ExerciseEntity(
            name = "Resistance Band Pull-Apart",
            targetMuscle = "SHOULDERS",
            secondaryMuscles = "Rear Delts, Rhomboids",
            equipment = "BANDS",
            isHomeFriendly = true,
            instructions = "Hold band at shoulder height, pull hands outward until band touches chest.",
            defaultRestSeconds = 45
        ),
        ExerciseEntity(
            name = "Hanging / Lying Leg Raise",
            targetMuscle = "CORE",
            secondaryMuscles = "Hip Flexors",
            equipment = "BODYWEIGHT",
            isHomeFriendly = true,
            instructions = "Engage abs to curl pelvis upward, controlling the eccentric descent.",
            defaultRestSeconds = 60
        )
    )
}