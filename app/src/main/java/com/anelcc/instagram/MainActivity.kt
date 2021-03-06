package com.anelcc.instagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anelcc.instagram.auth.LoginScreen
import com.anelcc.instagram.auth.SignupScreen
import com.anelcc.instagram.main.FeedScreen
import com.anelcc.instagram.main.NotificationMessage
import com.anelcc.instagram.main.PostScreen
import com.anelcc.instagram.main.SearchScreen
import com.anelcc.instagram.ui.theme.InstagramTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    instagramApp()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object SignUp: DestinationScreen("signUp")
    object Login: DestinationScreen("login")
    object Feed: DestinationScreen("feed")
    object Search: DestinationScreen("search")
    object Post: DestinationScreen("post")
}

@Composable
fun instagramApp() {
    val vm = hiltViewModel<InstagramViewModel>()
    val navController = rememberNavController()

    NotificationMessage(viewModel = viewModel())
    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route) {
        composable(DestinationScreen.SignUp.route) {
            SignupScreen(navController = navController, viewModel = vm)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController = navController, viewModel = vm)
        }
        composable(DestinationScreen.Feed.route) {
            FeedScreen(navController = navController, viewModel = vm)
        }
        composable(DestinationScreen.Search.route) {
            SearchScreen(navController = navController, viewModel = vm)
        }
        composable(DestinationScreen.Post.route) {
            PostScreen(navController = navController, viewModel = vm)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InstagramTheme {
//        Greeting("Android")
        instagramApp()
    }
}