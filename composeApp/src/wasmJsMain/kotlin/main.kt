import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import api.NavigationApi
import api.UserApi
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.User
import org.w3c.dom.Window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        val ioScope: CoroutineScope = rememberCoroutineScope { Dispatchers.Default }
        val dependencies = remember(ioScope) { buildDependencies(ioScope, window) }
        window.onpopstate = {
            it.state.toString()
        }
        App(dependencies)
    }
}


private fun buildDependencies(ioScope: CoroutineScope, window: Window): Dependencies =
    object : Dependencies {
        val depWindow = window

        override val ioScope: CoroutineScope = ioScope

        override val userApi: UserApi = object : UserApi {
            override suspend fun create(login: String, password: String): User? {
                depWindow.localStorage.setItem("login", login)
                depWindow.localStorage.setItem("password", password)
                return User(login, password)
            }

            override suspend fun signOut(): Boolean {
                depWindow.localStorage.clear()
                return true
            }

            override suspend fun getSavedUser(): User? {
                val login = depWindow.localStorage.getItem("login") ?: return null
                val pass = depWindow.localStorage.getItem("password") ?: return null
                return User(login, pass)
            }
        }

        override val navigationApi: NavigationApi = object : NavigationApi {
            override fun addToHistory(text: String) {
//                TODO handle browser history
                depWindow.history.pushState(title = text, data = text.toJsString())
            }
        }
    }