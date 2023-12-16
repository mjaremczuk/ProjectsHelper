package screen

import Dependencies
import Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.InputField
import component.LabelButton
import component.password
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(
    dependencies: Dependencies,
    onCreateAccountClick: () -> Unit,
) {

    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val repeatPassword = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Resources.createAccountHeader,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(modifier = Modifier.widthIn(max = 300.dp)) {
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    labelState = login,
                    showProgress = { false },
                    placeholder = {
                        Text(Resources.loginLabel)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    labelState = password,
                    showProgress = { false },
                    keyboardOptions = KeyboardOptions.Default.password(),
                    placeholder = {
                        Text(Resources.passwordLabel)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    labelState = repeatPassword,
                    showProgress = { false },
                    keyboardOptions = KeyboardOptions.Default.password(),
                    placeholder = {
                        Text(Resources.confirmPasswordLabel)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LabelButton(Modifier, Resources.createButton) {
                dependencies.ioScope.launch {
                    dependencies.userApi.create(login.value, password.value)
                    onCreateAccountClick()
                }
            }
        }
    }
}