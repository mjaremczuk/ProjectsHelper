package com.github.mjaremczuk.projects.helper

import App
import Dependencies
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import api.JiraApi
import api.NavigationApi
import api.ProjectApi
import api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val ioScope: CoroutineScope = rememberCoroutineScope { Dispatchers.IO }
            App(ProdDependencies(ioScope))
        }
    }
}

class ProdDependencies(override val ioScope: CoroutineScope) : Dependencies {
    override val userApi: UserApi
        get() = TODO("Not yet implemented")
    override val navigationApi: NavigationApi
        get() = TODO("Not yet implemented")
    override val projectApi: ProjectApi
        get() = TODO("Not yet implemented")
    override val jiraApi: JiraApi
        get() = TODO("Not yet implemented")
}

class DummyDeps(override val ioScope: CoroutineScope) : Dependencies {
    override val userApi: UserApi
        get() = TODO("Not yet implemented")
    override val navigationApi: NavigationApi
        get() = TODO("Not yet implemented")
    override val projectApi: ProjectApi
        get() = TODO("Not yet implemented")
    override val jiraApi: JiraApi
        get() = TODO("Not yet implemented")
}

@Preview
@Composable
fun AppAndroidPreview() {
    val ioScope: CoroutineScope = rememberCoroutineScope { Dispatchers.Default }
    App(DummyDeps(ioScope))
}