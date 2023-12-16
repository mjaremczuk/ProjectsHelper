package screen

import Dependencies
import Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.LabeledButton
import kotlinx.coroutines.launch
import model.User

@Composable
fun HomeScreen(
    dependencies: Dependencies,
    user: User,
    onSignOutAction: () -> Unit,
) {
//todo use scaffold
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        LabeledButton(
            modifier = Modifier,
            Resources.homeLogOutButton,
            onClick = {
                dependencies.ioScope.launch {
                    val result = dependencies.userApi.signOut()
                    if (result) {
                        onSignOutAction()
                    } else {
//                     TODO error handling
                    }
                }
            }
        )
        Spacer(Modifier.width(16.dp))
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Resources.homeWelcomeHeader,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(32.dp))
            Text(user.login)
        }
    }
}