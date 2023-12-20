package component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CheckItem(
    modifier: Modifier = Modifier,
    preselected: Boolean,
    onCheckBoxAction: (selected: Boolean) -> Unit,
    label: @Composable () -> Unit
) {

    val checked = remember { mutableStateOf(preselected) }

    Row(
        modifier = modifier.fillMaxWidth()
            .clickable {
                checked.value = checked.value.not()
                onCheckBoxAction(checked.value)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked.value,
            onCheckedChange = onCheckBoxAction,
        )
        Box(
            modifier = Modifier.weight(1f, fill = false),
            contentAlignment = Alignment.CenterStart
        ) {
            label()
        }
    }
}