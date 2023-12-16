import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.AddProject
import model.AddVersion
import model.CreateAccount
import model.EditVersion
import model.Home
import model.Login
import model.Page
import model.Settings
import model.Welcome
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screen.CreateAccountScreen
import screen.HomeScreen
import screen.LogInScreen
import screen.NavigationStack
import screen.WelcomeScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(dependencies: Dependencies) {
    MaterialTheme {
        val navigationStack = remember { NavigationStack<Page>(Welcome) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedContent(navigationStack.lastWithIndex()) { (index, page) ->
                when (page) {
                    Welcome -> WelcomeScreen(
                        dependencies = dependencies,
                        onContinueClick = {
                            dependencies.navigationApi.addToHistory("welcome")
                            navigationStack.push(Login)
                        },
                        onSavedUserAction = { user ->
                            navigationStack.push(Home(user))
                        })

                    Login -> LogInScreen(
                        dependencies = dependencies,
                        onContinueClick = {},
                        onCreateAccountClick = {
                            dependencies.navigationApi.addToHistory("login")
                            navigationStack.push(CreateAccount)
                        }
                    )

                    CreateAccount -> CreateAccountScreen(
                        dependencies = dependencies,
                        onCreateAccountClick = {
                            dependencies.navigationApi.addToHistory("create-account")
                        }
                    )

                    AddVersion -> TODO()

                    AddProject -> TODO()


                    EditVersion -> TODO()

                    is Home -> {
                        HomeScreen(
                            dependencies = dependencies,
                            user = page.user,
                        )
                    }

                    Settings -> TODO()
                }
            }
        }
    }
}