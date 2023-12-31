package api

import model.Project

interface ProjectApi {

    suspend fun getProjects(login: String): List<Project>
}