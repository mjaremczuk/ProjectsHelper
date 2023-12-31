import api.JiraApi
import api.NavigationApi
import api.ProjectApi
import api.UserApi
import kotlinx.coroutines.CoroutineScope

interface Dependencies {
    val ioScope: CoroutineScope

    val userApi: UserApi
    val navigationApi: NavigationApi
    val projectApi: ProjectApi
    val jiraApi: JiraApi
}