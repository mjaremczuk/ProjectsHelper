package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(showError: MutableState<String>) {
    AnimatedVisibility(showError.value.isNotBlank()) {
        Text(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth(),
            text = showError.value,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}