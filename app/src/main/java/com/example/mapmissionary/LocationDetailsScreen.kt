package com.example.mapmissionary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LocationDetailsScreen(navController: NavController?) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        ScreenTitle("Location")

        CardTitle("Address")
        DetailCard(sharedViewModel.selectedLocation.address ?: "Not found")

        CardTitle("Grid Reference")
        DetailCard(sharedViewModel.selectedLocation.gridRef ?: "Not found")

        CardTitle("Coordinates")
        DetailCard(sharedViewModel.selectedLocation.coordinates ?: "Not found")


        BackButton(navController)
    }

}


@Composable
fun CardTitle(title: String) {
    Text(
        text = "$title:",
        fontSize = 25.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(top = 30.dp)
    )
}

@Composable
fun DetailCard(content: String) {
    //TODO Move clipboard manager instance
    val clipboardManager = LocalClipboardManager.current
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        onClick = {
            //TODO Copy contents to clipboard
            clipboardManager.setText(AnnotatedString(content))
        }
    ) {
        Text(
            text = content,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(8.dp)

        )
    }
}

@Composable
fun ScreenTitle(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    )
}

@Composable
fun BackButton(navController: NavController?) {
    Button(
        onClick = { navController?.popBackStack() },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Go back")
    }

}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        ScreenTitle("Location")

        CardTitle("Address")
        DetailCard("No location")

        CardTitle("Grid Reference")
        DetailCard("No location")

        CardTitle("Coordinates")
        DetailCard("No location")


        BackButton(null)
    }
}
