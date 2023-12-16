package component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelButton(
    modifier: Modifier,
    label: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(
            modifier = modifier
                .padding(0.dp),
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
