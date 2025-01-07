package com.geekofia.dugulink.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekofia.dugulink.MainViewModel

@Composable
fun DashboardScreen(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Button(
            onClick = {
                mainViewModel.handleLogout()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
            )
        ) {
            Text("Logout")
        }

    }
}
