package screen

import Dependencies
import Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.LabeledButton
import kotlinx.coroutines.launch
import model.User

@Composable
fun WelcomeScreen(
    dependencies: Dependencies,
    onContinueClick: () -> Unit,
    onSavedUserAction: (User) -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Resources.welcomeHeader,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(Resources.welcomeDescription, color = Color.Gray)
            Spacer(modifier = Modifier.height(64.dp))
            LabeledButton(Modifier, Resources.welcomeButtonLabel, onClick = onContinueClick)
        }
    }
    LaunchedEffect(Unit) {
        dependencies.ioScope.launch {
            dependencies.userApi.getSavedUser()?.let { user ->
                onSavedUserAction(user)
            }
        }
    }
}