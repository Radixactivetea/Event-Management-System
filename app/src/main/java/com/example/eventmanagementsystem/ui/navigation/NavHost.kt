package com.example.eventmanagementsystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.eventmanagementsystem.ui.eventManagement.DetailEvent
import com.example.eventmanagementsystem.ui.eventManagement.DetailEventDestination
import com.example.eventmanagementsystem.ui.eventManagement.EventDetailViewModel
import com.example.eventmanagementsystem.ui.eventManagement.EventMainPage
import com.example.eventmanagementsystem.ui.eventManagement.EventMainPageDestination
import com.example.eventmanagementsystem.ui.eventManagement.EventMainViewModel
import com.example.eventmanagementsystem.ui.taskManagement.AddTaskDestination
import com.example.eventmanagementsystem.ui.taskManagement.AddTaskPage
import com.example.eventmanagementsystem.ui.taskManagement.AddTaskViewModel
import com.example.eventmanagementsystem.ui.taskManagement.TaskPage
import com.example.eventmanagementsystem.ui.taskManagement.TaskPageDestination
import com.example.eventmanagementsystem.ui.taskManagement.TaskViewModel
import com.example.eventmanagementsystem.ui.userAuth.LoginScreen
import com.example.eventmanagementsystem.ui.userAuth.LoginScreenDestination
import com.example.eventmanagementsystem.ui.userAuth.LoginViewModel
import com.example.eventmanagementsystem.ui.userAuth.RegisterScreen
import com.example.eventmanagementsystem.ui.userAuth.RegisterScreenDestination
import com.example.eventmanagementsystem.ui.userAuth.RegisterViewModel

@Composable
fun EventManagementNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination.route,
        modifier = modifier
    ) {

        composable(route = LoginScreenDestination.route){
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                navigateToEvent = { navController.navigate(EventMainPageDestination.route) },
                navigateToRegister = { navController.navigate(RegisterScreenDestination.route) },
                viewModel = loginViewModel
            )
        }

        composable(route = RegisterScreenDestination.route){
            val registerViewModel: RegisterViewModel = viewModel()

            RegisterScreen(
                navigateToLogin = { navController.navigate(LoginScreenDestination.route) },
                navigateToEvent = { navController.navigate(EventMainPageDestination.route) },
                viewModel = registerViewModel
            )
        }

        composable(route = EventMainPageDestination.route){
            val eventViewModel: EventMainViewModel = viewModel()

            EventMainPage(
                //navigateToProfile = { navController.navigate(ProfilePageDestination.route) },
                //navigateToNotifications = { navController.navigate(NotificationsPageDestination.route) },
                navigateToDetailEvent = { navController.navigate("${DetailEventDestination.route}/${it}") },
                viewModel = eventViewModel
            )
        }

        composable(
            route = DetailEventDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailEventDestination.eventIdArg) {
                type = NavType.IntType
            })
        ){
            val eventDetailViewModel: EventDetailViewModel = viewModel()

            DetailEvent(
                navigateBack = { navController.navigateUp() },
                //navigateToProfile = { navController.navigate(ProfilePageDestination.route) },
                //navigateToNotifications = { navController.navigate(NotificationsPageDestination.route) },
                //navigateToTask = { navController.navigate(TaskPageDestination.route) },
                eventId = navController.currentBackStackEntry?.arguments?.getInt("eventId") ?: 0,
                viewModel = eventDetailViewModel
            )
        }

        composable(route = TaskPageDestination.route){
            val taskViewModel: TaskViewModel = viewModel()

            TaskPage(
                navigateToAddTask = { navController.navigate(AddTaskDestination.route) },
                viewModel = taskViewModel
            )
        }

        composable(route = AddTaskDestination.route){
            val taskViewModel: AddTaskViewModel = viewModel()

            AddTaskPage(
                onNavigateUp = { navController.popBackStack()},
                navigateBack = { navController.navigateUp()},
                viewModel = taskViewModel
            )
        }
    }
}