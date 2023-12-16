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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.CheckItem
import component.InputField
import component.LabelButton
import component.password
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    dependencies: Dependencies,
    onContinueClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
) {

    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val rememberLogin = remember { mutableStateOf(false) }

    val showError = remember { mutableStateOf(false) }

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
                    placeholder = {
                        Text(Resources.passwordLabel)
                    }
                )

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
                LabelButton(Modifier, Resources.loginButton) {
                    dependencies.ioScope.launch {
                        val result = dependencies.userApi.create(
                            login = login.value,
                            password = password.value
                        )
                        if (result) {
                            onContinueClick()
                        } else {
                            showError.value = true
                        }
                    }
                }
                Spacer(Modifier.width(16.dp))
                LabelButton(Modifier, Resources.createAccountButton, onCreateAccountClick)
                if (showError.value) {
                    Text("error!")
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        dependencies.ioScope.launch {
            dependencies.userApi.getSavedUser()?.let {
                login.value = it.login
                password.value = it.password
            }
        }
    }
}