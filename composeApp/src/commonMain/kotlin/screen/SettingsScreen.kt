package screen

import Dependencies
import Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import component.InputField
import component.LabeledButton
import kotlinx.coroutines.launch
import model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalResourceApi
@Composable
fun SettingsScreen(
    dependencies: Dependencies,
    getUser: () -> User,
    onBack: () -> Unit,
) {
    Column {
        TopAppBar(
            title = { Text(Resources.settingsHeader) },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                Row {
                    Image(
                        painterResource("ic_arrow_back.xml"),
                        "Back button",
                        modifier = Modifier.clickable { onBack() }
                            .padding(16.dp)
                    )
                }
            },
            actions = {
                Row {
                    Text(
                        "Save",
                        modifier = Modifier
                            .clickable {
                                dependencies.ioScope.launch {
                                    onBack()
                                }
                            }.padding(16.dp)
                    )
                }
            },
            backgroundColor = Color.Blue,
            contentColor = Color.White
        )
        SettingsContent()
    }
}

@Composable
fun SettingsContent() {
    val clipboardManager = LocalClipboardManager.current

    val name = remember { mutableStateOf("") }
    val token = remember { mutableStateOf("") }
    val jiraProductUrl = remember { mutableStateOf("") }

    val listState = rememberScrollState()

    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(listState),
    ) {

        Text(Resources.settingsJiraEmailHeader)
        InputField(
            modifier = Modifier.weight(0.5f),
            labelState = name,
            showProgress = { false },
            placeholder = {
                Text(Resources.settingsJiraEmailPlaceholder)
            }
        )
        Spacer(Modifier.height(8.dp))
        LabeledButton(
            modifier = Modifier.padding(vertical = 8.dp),
            label = Resources.settingsSave,
            onClick = {
            }
        )

        Spacer(Modifier.height(16.dp))
        Text(Resources.settingsTokenHeader)
        InputField(
            modifier = Modifier.weight(0.5f),
            labelState = token,
            showProgress = { false },
            placeholder = {
                Text(Resources.settingsTokenPlaceholder)
            }
        )
        ClickableUrl(clipboardManager)
        Spacer(Modifier.height(8.dp))
        LabeledButton(
            modifier = Modifier.padding(vertical = 8.dp),
            label = Resources.settingsSave,
            onClick = {}
        )

        Spacer(Modifier.height(16.dp))
        Text(text = Resources.settingsJiraUrlHaeader)
        InputField(
            modifier = Modifier.weight(0.5f),
            labelState = jiraProductUrl,
            showProgress = { false },
            placeholder = {
                Text(Resources.loginLabel)
            }
        )
        LabeledButton(
            modifier = Modifier.padding(vertical = 8.dp),
            label = Resources.settingsSave,
            onClick = {
            }
        )
    }
}

@Composable
fun ClickableUrl(clipboardManager: ClipboardManager) {
    val text = buildAnnotatedString {
        append("To get your own token ")
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue, fontWeight = FontWeight.Bold
            )
        ) {
            append("copy this link")
        }
        append(" to clipboard")
        pop()
    }
    ClickableText(text, onClick = {
        text.getStringAnnotations(
            tag = "URL", start = it, end = it
        ).firstOrNull()?.let { annotation ->
            clipboardManager.setText(AnnotatedString(annotation.item))
//            add toast message when copied
        }
    })
//todo use expect composable to add selection for desktop only

//    SelectionContainer {
//        Text(
//            "https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/",
//            color = Color.Blue,
//            fontSize = 12.sp
//        )
//    }
}