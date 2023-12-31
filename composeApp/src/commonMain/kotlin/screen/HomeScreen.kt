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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.LabeledButton
import kotlinx.coroutines.launch
import model.JiraAccountDetails
import model.Project
import model.User
import screen.state.HomeState

@Composable
fun HomeScreen(
    dependencies: Dependencies,
    user: User,
    onSignOutAction: () -> Unit,
    onAddProject: (User) -> Unit,
    onAddVersion: (User) -> Unit,
    onAddCredentials: (User) -> Unit,
) {
//todo use scaffold
    val state = remember { mutableStateOf<HomeState>(HomeState.Welcome) }
    val credentials = remember { mutableStateOf<JiraAccountDetails?>(null) }
    val projects = remember { mutableStateOf<List<Project>?>(emptyList()) }

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
    when (state.value) {
        HomeState.MissingAuthorizationDetails -> MissingAuthorizationDetailsScreen(
            getUserLogin = { user.login },
            onClick = { onAddCredentials(user) },
        )

        HomeState.MissingProjects -> MissingProjectsScreen(
            getUserLogin = { user.login },
            onClick = { onAddProject(user) },
        )

        is HomeState.Success -> FixVersionsScreen(
            getUserLogin = { user.login },
            onClick = {}
        )

        HomeState.Welcome -> WelcomeScreen(
            getUserLogin = { user.login },
        )
    }

    LaunchedEffect(user, credentials) {
        dependencies.ioScope.launch {
            val authDetails = dependencies.userApi.getUserAuthorizationDetails(user.login)
            val projects = dependencies.projectApi.getProjects(user.login)
            if (authDetails != null) {
                credentials.value = authDetails

                if (projects.isEmpty()) {
                    state.value = HomeState.MissingProjects
                } else {
                    val data = dependencies.jiraApi.getFixVersions(user.login, false)
                    state.value = HomeState.Success(data)
                }
            } else {
                state.value = HomeState.MissingAuthorizationDetails
            }
        }
    }
}

@Composable
internal fun WelcomeScreen(
    getUserLogin: () -> String,
    content: @Composable () -> Unit = {},
) {
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
            Text(
                text = getUserLogin(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(Modifier.height(64.dp))
            content()
        }
    }
}

@Composable
internal fun MissingAuthorizationDetailsScreen(
    getUserLogin: () -> String,
    onClick: () -> Unit
) {
    WelcomeScreen(getUserLogin) {
        Text("Set up jira credentials to start using app")
        LabeledButton(Modifier, "Set up credentials", onClick)
    }
}

@Composable
internal fun MissingProjectsScreen(
    getUserLogin: () -> String,
    onClick: () -> Unit
) {
    WelcomeScreen(getUserLogin) {
        Text("Add projects you want to work on (existing projects you have access to in Jira) to start using app")
        LabeledButton(Modifier, "Add Project (s)", onClick)
    }
}

@Composable
internal fun FixVersionsScreen(
    getUserLogin: () -> String,
    onClick: () -> Unit
) {
    WelcomeScreen(getUserLogin) {
        Text("Here you will see fix version from your project(s)")
    }
}