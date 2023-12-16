package api

import model.User

interface UserApi {

    suspend fun create(login: String, password: String): Boolean //todo return model instead
    suspend fun getSavedUser(): User?
}