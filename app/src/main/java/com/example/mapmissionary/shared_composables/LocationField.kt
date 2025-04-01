package com.example.mapmissionary.shared_composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationField(title: String, content: String) {
    Column {
        CardTitle(title)
        DetailCard(content)
    }
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = "$title:",
        fontSize = 25.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .padding(top = 20.dp)
    )
}

@Composable
fun DetailCard(content: String) {
    val clipboardManager = LocalClipboardManager.current
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
        colors = CardColors(
            MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = { clipboardManager.setText(AnnotatedString(content)) }
    ) {
        Text(
            text = content,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(12.dp)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationField() {
    LocationField("Title", "Some content information",
    )
}