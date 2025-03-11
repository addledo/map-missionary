package com.example.mapmissionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapmissionary.ui.theme.MapMissionaryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gridRefService = GridRefService()

        setContent {
            MapMissionaryTheme {
                HomePage(gridRefService)
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun GetRefPreview() {
    MapMissionaryTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Prev text",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 150.dp)
                    .padding(30.dp),
                fontSize = 20.sp
            )

            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(100.dp)
            ) {
                Text("Get Quote")
            }
        }

    }
}
