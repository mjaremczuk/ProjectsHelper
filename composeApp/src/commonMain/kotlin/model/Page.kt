package model

sealed class Page

data object Welcome : Page()
data object Login : Page()
data object CreateAccount : Page()
data class Home(val user: User) : Page()

data object AddProject : Page()
data object AddVersion : Page()
data object EditVersion : Page()
data object Settings : Page()
