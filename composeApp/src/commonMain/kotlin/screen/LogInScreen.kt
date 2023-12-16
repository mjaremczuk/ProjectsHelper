package screen

import Dependencies
import Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.CheckItem
import component.ErrorView
import component.InputField
import component.LabeledButton
import component.password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.User

@Composable
fun LogInScreen(
    dependencies: Dependencies,
    onContinueClick: (User) -> Unit,
    onCreateAccountClick: () -> Unit,
) {

    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val rememberLogin = remember { mutableStateOf(false) }
    val passwordVisible = remember { mutableStateOf(false) }

    val showError = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Resources.loginHeader,
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
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    placeholder = {
                        Text(Resources.passwordLabel)
                    }
                )
                ErrorView(showError)
                Spacer(modifier = Modifier.height(8.dp))
                CheckItem(
                    modifier = Modifier,
                    preselected = rememberLogin.value,
                    onCheckBoxAction = {
                        rememberLogin.value = it
                    }
                ) {
                    Text(
                        Resources.rememberMe,
                        modifier = Modifier.padding(start = 4.dp, end = 16.dp),
                        color = Color.Black,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                LabeledButton(Modifier, Resources.loginButton) {
                    dependencies.ioScope.launch {
                        if (login.value.isBlank() || password.value.isBlank()) {
                            showError.value =  Resources.enterLoginPassword
                        } else {
                            val result = dependencies.userApi.create(
                                login = login.value,
                                password = password.value
                            )
                            if (result != null) {
                                onContinueClick(result)
                            } else {
                                showError.value = Resources.failedToCreateAccount
                            }
                        }
                    }
                }
                Spacer(Modifier.width(16.dp))
                LabeledButton(Modifier, Resources.createAccountButton, onCreateAccountClick)
            }
        }
    }
    LaunchedEffect(Unit) {
        dependencies.ioScope.launch {
            showError.value = ""
            dependencies.userApi.getSavedUser()?.let {
                login.value = it.login
                password.value = it.password
            }
        }
    }
}

private fun CoroutineScope.signIn() {

}