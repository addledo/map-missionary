package com.example.mapmissionary.shared_composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BackButton(navController: NavController?) {
    Button(
        onClick = {
            // The arguments to this function prevent navigating to a blank screen as a result
            // of multiple successive button clicks. This can happen by accident during the
            // transition between pages.
            // The function will pop off the navigation stack until it reaches the specified
            // screen. The false argument makes this uninclusive, so the specified screen will not
            // also be popped.
            navController?.popBackStack("search", false)
                  },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(horizontal = 10.dp)
            .height(50.dp)
    ) {
        Text(
            text = "Go back",
            fontSize = 20.sp
        )
    }
}
