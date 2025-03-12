package com.example.mapmissionary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val visiblePermissionDialogueQueue = mutableStateListOf<String>()

    fun dismissDialogue() {
        // removeLast() is not supported before API 35
        visiblePermissionDialogueQueue.removeAt(visiblePermissionDialogueQueue.lastIndex)
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (!isGranted) {
            // addFirst() not supported before API 35
            visiblePermissionDialogueQueue.add(0, permission)
        }
    }



}