package model

import androidx.compose.runtime.Stable

@Stable
data class User(
    val login: String,
    val password: String,
)
