package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    labelState: MutableState<String>?,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    showProgress: () -> Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    AnimatedVisibility(showProgress().not()) {
        TextField(
            modifier = modifier,
            value = labelState?.value.orEmpty(),
            label = label,
            placeholder = placeholder,
            onValueChange = { labelState?.value = it },
            keyboardOptions = keyboardOptions,
        )
    }
    if (showProgress()) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

@Stable
fun KeyboardOptions.password() = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)