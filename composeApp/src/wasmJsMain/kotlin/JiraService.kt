@file:OptIn(ExperimentalEncodingApi::class)

import api.JiraApi
import api.ProjectApi
import api.UserApi
import model.VersionModel
import org.w3c.dom.Window
import kotlin.io.encoding.ExperimentalEncodingApi

class JiraService(
    private val window: Window,
    private val projectApi: ProjectApi,
    private val userApi: UserApi,
) : JiraApi {

    override suspend fun getFixVersions(login: String, forceRefresh: Boolean): List<VersionModel> {
        return emptyList()
    }

    override suspend fun updateVersion(value: VersionModel, new: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createFixVersion(fixVersionName: String, selectedProjects: List<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun getTicketsFor(name: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFixVersion(versionModel: VersionModel) {
        TODO("Not yet implemented")
    }
}