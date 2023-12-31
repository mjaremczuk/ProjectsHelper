import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import api.JiraApi
import api.NavigationApi
import api.ProjectApi
import api.UserApi
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

        override val userApi: UserApi by lazy { UserService(depWindow) }

        override val jiraApi: JiraApi by lazy { JiraService(depWindow, projectApi, userApi) }

        override val navigationApi: NavigationApi = object : NavigationApi {
            override fun addToHistory(text: String) {
//                TODO handle browser history
                depWindow.history.pushState(title = text, data = text.toJsString())
            }
        }

        override val projectApi: ProjectApi by lazy { ProjectService(depWindow) }
    }