import api.UserApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.JiraAccountDetails
import model.User
import org.w3c.dom.Window


class UserService(private val depWindow: Window) : UserApi {

    companion object {
        private const val LOGIN_KEY = "login"
        private const val PASSWORD_KEY = "password"
        private const val AUTH_SUFFIX = "_auth"
    }

    override suspend fun create(login: String, password: String): User {
        depWindow.localStorage.setItem(LOGIN_KEY, login)
        depWindow.localStorage.setItem(PASSWORD_KEY, password)
        return User(login, password)
    }

    override suspend fun signOut(): Boolean {
        depWindow.localStorage.clear()
        return true
    }

    override suspend fun setUserAuthorizationDetails(
        login: String,
        auth: JiraAccountDetails
    ) {
        depWindow.localStorage.setItem("${login}$AUTH_SUFFIX", Json.encodeToString(auth))
    }

    override suspend fun getUserAuthorizationDetails(login: String): JiraAccountDetails? {
        val text = depWindow.localStorage.getItem("${login}$AUTH_SUFFIX")
        return text?.let {
            Json.decodeFromString<JiraAccountDetails>(it)
        }
    }

    override suspend fun getSavedUser(): User? {
        val login = depWindow.localStorage.getItem(LOGIN_KEY) ?: return null
        val pass = depWindow.localStorage.getItem(PASSWORD_KEY) ?: return null
        return User(login, pass)
    }
}