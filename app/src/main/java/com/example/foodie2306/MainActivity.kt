package com.example.foodie2306

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodie2306.screens.AnimationScreen
import com.example.foodie2306.screens.BasketScreen
import com.example.foodie2306.screens.MainScreen
import com.example.foodie2306.screens.SearchScreen
import com.example.foodie2306.ui.theme.Foodie2306Theme
import com.example.foodie2306.viewmodel.ProductsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Foodie2306Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   KoinContext {
                       val navController = rememberNavController()
                       val productsViewModel = koinViewModel<ProductsViewModel>()

                       NavHost(navController = navController, startDestination = "animation"){
                           composable(route = "animation"){
                               AnimationScreen(
                                   modifier = Modifier.padding(innerPadding),
                                   onAnimationComplete = {
                                       navController.navigate("mainScreen") {
                                           popUpTo("animation") { inclusive = true }
                                       }
                                   }

                               )
                           }
                           composable(route = "mainScreen"){
                               MainScreen(
                                   modifier = Modifier.padding(innerPadding),
                                   navController = navController,
                                   productsViewModel = productsViewModel
                               )
                           }
                           composable(route = "basketScreen"){
                               BasketScreen(
                                   modifier = Modifier.padding(innerPadding),
                                   navController = navController
                               )
                           }

                           composable(route = "search") {
                               SearchScreen(
                                   modifier = Modifier.padding(innerPadding),
                                   productsViewModel = productsViewModel,
                                   onBackClick = { navController.popBackStack() } // Handle back navigation
                               )
                           }



                       }
                   }
                }
            }
        }
    }
}

