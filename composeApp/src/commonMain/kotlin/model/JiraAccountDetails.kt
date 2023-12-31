package model

import kotlinx.serialization.Serializable

@Serializable
data class JiraAccountDetails(
    val email: String,
    val token: String,
    val ticketBaseUrl: String,
)