package com.example.mapmissionary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = Color(0xEAEAEAEA),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(text = title, fontSize = 20.sp)
                Text(text = message.replace("java.lang.Exception: ", ""))
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewInfoDialog() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp)
            .background(Color.LightGray)
    ) {
        InfoDialog(
            title = "Information",
            message = "Information dialog",
            onDismiss = {}
        )
    }

}