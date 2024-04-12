package com.example.compose1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose1.ui.account.AccountScreen
import com.example.compose1.ui.addtransaction.AddTransactionScreen
import com.example.compose1.ui.category.CategoryScreen
import com.example.compose1.ui.main.MainScreen


enum class Routes {
    MainScreen,
    CategoryScreen,
    AccountScreen,
    AddTransactionScreen
}

@Composable
fun CashfyNavigation(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.MainScreen.name
    ) {
        composable(route = Routes.MainScreen.name) {
            MainScreen(onNavigate = {id ->
                navHostController.navigate(route = "${Routes.AddTransactionScreen.name}?id=$id")
            },
                navController = navHostController)
        }
        composable(route = Routes.CategoryScreen.name) {
            CategoryScreen(navController = navHostController)
        }
        composable(route = Routes.AccountScreen.name){
            AccountScreen(navController = navHostController)
        }
        composable(
            route = "${Routes.AddTransactionScreen.name}?id={id}",
            arguments = listOf(navArgument("id") {type = NavType.IntType})
        ){
            val id = it.arguments?.getInt("id") ?: -1
            AddTransactionScreen(id = id){
                navHostController.navigateUp()
            }
        }
    }
}