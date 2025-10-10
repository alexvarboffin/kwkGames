package com.esporte.olimp.yay

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.esporte.olimp.yay.presentation.ViewModelFactory
import com.esporte.olimp.yay.presentation.addhorse.AddHorseViewModel
import com.esporte.olimp.yay.presentation.addhealthdata.AddHealthDataViewModel
import com.esporte.olimp.yay.presentation.health.HealthViewModel
import com.esporte.olimp.yay.presentation.home.HomeViewModel
import com.esporte.olimp.yay.presentation.newtraining.NewTrainingViewModel
import com.esporte.olimp.yay.presentation.profile.ProfileViewModel
import com.esporte.olimp.yay.presentation.schedule.ScheduleViewModel
import com.esporte.olimp.yay.presentation.trainings.TrainingsViewModel
import com.esporte.olimp.yay.ui.addhorse.AddHorseScreen
import com.esporte.olimp.yay.ui.addhealthdata.AddHealthDataScreen
import com.esporte.olimp.yay.ui.health.HealthScreen
import com.esporte.olimp.yay.ui.home.HomeScreen
import com.esporte.olimp.yay.ui.newtraining.NewTrainingScreen
import com.esporte.olimp.yay.ui.profile.ProfileScreen
import com.esporte.olimp.yay.ui.schedule.ScheduleScreen
import com.esporte.olimp.yay.ui.trainings.TrainingsScreen
import com.walhalla.sdk.utils.loadPrivacyPolicy

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Schedule : Screen("schedule", "Schedule", Icons.Default.DateRange)
    object Trainings : Screen("trainings", "Trainings", Icons.Default.Star)
    object Health : Screen("health", "Health", Icons.Default.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object AddHorse : Screen("add_horse", "Add Horse", Icons.Default.Add)
    object NewTraining : Screen("new_training", "New Training", Icons.Default.Add)
    object AddHealthData : Screen("add_health_data", "Add Health Data", Icons.Default.Add)
}

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this).apply {} //not set WebViewClient!!!
        val appContainer = (application as HorseApplication).appContainer
        setContent {
            HorseTrainerTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        
                        composable(Screen.Home.route) {
                            val viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            HomeScreen(
                                uiState = viewModel.uiState.collectAsState().value,
                                onAddHorseClick = { navController.navigate(Screen.AddHorse.route) },
                                onNewTrainingClick = { navController.navigate(Screen.NewTraining.route) },
                                onAddHealthDataClick = { navController.navigate(Screen.AddHealthData.route) }
                            )
                        }
                        composable(Screen.Schedule.route) {
                            val viewModel: ScheduleViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            ScheduleScreen(uiState = viewModel.uiState.collectAsState().value)
                        }
                        composable(Screen.Trainings.route) {
                            val viewModel: TrainingsViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            TrainingsScreen(uiState = viewModel.uiState.collectAsState().value, onNavigateToNewTraining = { navController.navigate(Screen.NewTraining.route) })
                        }
                        composable(Screen.Health.route) {
                            val viewModel: HealthViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            HealthScreen(uiState = viewModel.uiState.collectAsState().value, onNavigateToAddHealthData = { navController.navigate(Screen.AddHealthData.route) })
                        }
                        composable(Screen.Profile.route) {
                            val viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            ProfileScreen(uiState = viewModel.uiState.collectAsState().value, onAddHorseClick = { navController.navigate(Screen.AddHorse.route) })
                        }
                        composable(Screen.AddHorse.route) {
                            val viewModel: AddHorseViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            AddHorseScreen(
                                onSaveClick = { name, breed, dob, gender, photoPath, weight, weightGoal, bodyTemperature, pulse, respiration, height, acthLevel ->
                                    viewModel.addHorse(name, breed, dob, gender, photoPath, weight, weightGoal, bodyTemperature, pulse, respiration, height, acthLevel)
                                    navController.popBackStack()
                                },
                                onCancelClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.NewTraining.route) {
                            val viewModel: NewTrainingViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            NewTrainingScreen(
                                onSaveClick = { name, date, duration, surfaceType, distance, avgSpeed, transitionsWalk, transitionsTrot, notes ->
                                    viewModel.addTraining(name, date, duration, surfaceType, distance, avgSpeed, transitionsWalk, transitionsTrot, notes)
                                    navController.popBackStack()
                                },
                                onCancelClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.AddHealthData.route) {
                            val viewModel: AddHealthDataViewModel = viewModel(factory = ViewModelFactory(appContainer))
                            AddHealthDataScreen(
                                onSaveClick = { date, weight, bodyTemperature, pulse, respiration, acthLevel, comment ->
                                    viewModel.addHealthRecord(date, weight, bodyTemperature, pulse, respiration, acthLevel, comment)
                                    navController.popBackStack()
                                },
                                onCancelClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }

    private lateinit var webView: WebView


    override fun onResume() {
        super.onResume()
        webView.loadPrivacyPolicy(Const.site)
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Schedule,
        Screen.Trainings,
        Screen.Health,
        Screen.Profile
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
