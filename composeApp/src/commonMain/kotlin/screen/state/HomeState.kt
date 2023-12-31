package screen.state

import model.VersionModel

sealed class HomeState {
    data object Welcome : HomeState()
    data object MissingAuthorizationDetails : HomeState()
    data object MissingProjects : HomeState()
    data class Success(val versions: List<VersionModel>) : HomeState()
}