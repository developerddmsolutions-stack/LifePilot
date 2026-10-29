package com.lifepilot.personal.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import com.lifepilot.personal.ui.screens.dashboard.DashboardScreen
import com.lifepilot.personal.ui.screens.nutrition.NutritionScreen
import com.lifepilot.personal.ui.screens.routine.RoutineScreen
import com.lifepilot.personal.ui.screens.workout.WorkoutScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Dashboard : Screen("dashboard", "Today", Icons.Default.Home)
    data object Routine : Screen("routine", "Schedule", Icons.Default.Schedule)
    data object Workout : Screen("workout", "Training", Icons.Default.FitnessCenter)
    data object Nutrition : Screen("nutrition", "Fuel", Icons.Default.Restaurant)
}

@Composable
fun MainAppScaffold() {
    val navController = rememberNavController()
    val items = listOf(Screen.Dashboard, Screen.Routine, Screen.Workout, Screen.Nutrition)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen() }
            composable(Screen.Routine.route) { RoutineScreen() }
            composable(Screen.Workout.route) { WorkoutScreen() }
            composable(Screen.Nutrition.route) { NutritionScreen() }
        }
    }
}