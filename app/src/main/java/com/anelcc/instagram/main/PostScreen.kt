package com.anelcc.instagram.main

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anelcc.instagram.InstagramViewModel


@Composable
fun PostScreen(navController: NavController, viewModel: InstagramViewModel) {
    val userData = viewModel.userData.value
    val isLoading = viewModel.isInProgress.value

    Column() {
        Column(modifier = Modifier.weight(1F)) {
            Row {
                ProfileImage(userData?.imageURL) {
                    
                }
                Text(
                    text = "15\npost",
                    modifier = Modifier
                        .weight(1F)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center)
                Text(
                    text = "54\nfollowers",
                    modifier = Modifier
                        .weight(1F)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center)

                Text(
                    text = "95\nfollowing",
                    modifier = Modifier
                        .weight(1F)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center)
            }
            Column(modifier = Modifier.padding(8.dp)) {
                val usernameDisplay = if (userData?.username == null) "" else "@${userData?.username}"
                Text(text = userData?.name?: "", fontWeight = FontWeight.Bold)
                Text(text = usernameDisplay)
                Text(text = userData?.bio?: "")
            }
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp, disabledElevation = 0.dp),
                shape = RoundedCornerShape(10)
            ){
                Text(text = "Edit Profile", color = Color.Black)
            }
            Column(modifier = Modifier.weight(1F)) {
                Text(text = "Post List", color = Color.Black)
            }
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.POST,
            navController = navController
        )
    }



/*    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier.weight(1F)
        ) {Text(text = "Post Screen") }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.POST,
            navController = navController
        )
    }*/
}

@Composable
fun ProfileImage(imageUrl: String?, onClick: () -> Unit) {

}
