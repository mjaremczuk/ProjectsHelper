import api.NavigationApi
import api.UserApi
import kotlinx.coroutines.CoroutineScope

interface Dependencies {
    val ioScope: CoroutineScope

    val userApi: UserApi
    val navigationApi: NavigationApi
}