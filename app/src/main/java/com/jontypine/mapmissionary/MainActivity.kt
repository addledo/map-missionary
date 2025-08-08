package com.jontypine.mapmissionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jontypine.mapmissionary.navigation.AppNavigation
import com.jontypine.mapmissionary.ui.theme.MapMissionaryTheme
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