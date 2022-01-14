package com.anelcc.instagram.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.anelcc.instagram.InstagramViewModel


@Composable
fun FeedScreen(navController: NavController, viewModel: InstagramViewModel) {
    Text(text = "Feed Screen")
}