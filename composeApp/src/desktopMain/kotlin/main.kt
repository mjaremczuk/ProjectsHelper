import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import api.NavigationApi
import api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "ProjectsHelper") {
        val ioScope: CoroutineScope = rememberCoroutineScope { Dispatchers.IO }
        App(ProdDependencies(ioScope))
    }
}

class ProdDependencies(override val ioScope: CoroutineScope) : Dependencies {
    override val userApi: UserApi
        get() = TODO("Not yet implemented")
    override val navigationApi: NavigationApi
        get() = TODO("Not yet implemented")
}

class PreviewDependencies(override val ioScope: CoroutineScope) : Dependencies {
    override val userApi: UserApi
        get() = TODO("Not yet implemented")
    override val navigationApi: NavigationApi
        get() = TODO("Not yet implemented")
}

@Preview
@Composable
fun AppDesktopPreview() {
    val ioScope: CoroutineScope = rememberCoroutineScope { Dispatchers.Default }
    App(PreviewDependencies(ioScope))
}