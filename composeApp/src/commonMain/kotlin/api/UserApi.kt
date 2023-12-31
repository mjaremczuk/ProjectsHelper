package api

import model.JiraAccountDetails
import model.User

interface UserApi {

    suspend fun create(login: String, password: String): User? //todo return model instead
    suspend fun getSavedUser(): User?
    suspend fun signOut(): Boolean
    suspend fun getUserAuthorizationDetails(login: String): JiraAccountDetails?
    suspend fun setUserAuthorizationDetails(login: String, auth: JiraAccountDetails)
}