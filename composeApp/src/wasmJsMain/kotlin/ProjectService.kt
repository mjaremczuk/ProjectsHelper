import api.ProjectApi
import kotlinx.serialization.json.Json
import model.Project
import org.w3c.dom.Window

class ProjectService(private val depWindow: Window) : ProjectApi {

    override suspend fun getProjects(login: String): List<Project> {
        return listOf(
            Project("MOB", 12944)
        )
        val projects = depWindow.localStorage.getItem("${login}_projects")
        return projects?.let { Json.decodeFromString<List<Project>>(it) } ?: emptyList()
    }
}