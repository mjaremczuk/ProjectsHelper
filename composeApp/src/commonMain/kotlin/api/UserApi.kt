package api

import model.User

interface UserApi {

    suspend fun create(login: String, password: String): User? //todo return model instead
    suspend fun getSavedUser(): User?
    suspend fun signOut(): Boolean
}