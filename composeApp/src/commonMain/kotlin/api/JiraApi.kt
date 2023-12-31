package api

import model.VersionModel

interface JiraApi {

    suspend fun getFixVersions(login: String, forceRefresh: Boolean): List<VersionModel>
    suspend fun updateVersion(value: VersionModel, new: String)
    suspend fun createFixVersion(fixVersionName: String, selectedProjects: List<Int>)
    suspend fun getTicketsFor(name: String): List<String>

    suspend fun removeFixVersion(versionModel: VersionModel)
}