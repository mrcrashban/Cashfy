package com.example.compose1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose1.CashfyApplication
import com.example.compose1.Graph
import com.example.compose1.ui.account.AccountScreen
import com.example.compose1.ui.addtransaction.AddTransactionScreen
import com.example.compose1.ui.category.CategoryScreen
import com.example.compose1.ui.main.MainScreen


enum class Routes {
    Main,
    Category,
    Account,
    AddTransaction
}

@Composable
fun CashfyNavigation(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = "MainScreen"
    ) {
        composable("MainScreen") {
            MainScreen(navController = navHostController)
        }
        composable("CategoryScreen") {
            CategoryScreen(navController = navHostController)
        }
        composable("AccountScreen"){
            AccountScreen(navController = navHostController)
        }
        composable("AddTransactionScreen"){
            AddTransactionScreen(navController = navHostController)
        }
    }
}