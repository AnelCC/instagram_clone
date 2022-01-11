package com.anelcc.instagram.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anelcc.instagram.DestinationScreen
import com.anelcc.instagram.InstagramViewModel
import com.anelcc.instagram.main.navigateTo


@Composable
fun LoginScreen(navController: NavController, viewModel: InstagramViewModel) {
    Text(text = "New Here? Go To Sign Up ->",
        color = Color.Blue,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateTo(navController, DestinationScreen.SignUp)
            }
    )
}
