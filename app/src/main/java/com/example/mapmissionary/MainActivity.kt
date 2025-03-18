package com.example.mapmissionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mapmissionary.navigation.AppNavigation
import com.example.mapmissionary.ui.theme.MapMissionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MapMissionaryTheme {
                AppNavigation()
            }
        }
    }
}