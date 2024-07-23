package ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.CommonUiState
import core.NetworkState
import core.ProgressBarState
import core.UIComponent
import haloapp.composeapp.generated.resources.Res
import haloapp.composeapp.generated.resources.logo
import haloapp.composeapp.generated.resources.no_wifi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.core.theme.Spacer_16dp
import ui.login.view_model.LoginEvent

/**
 * @param queue: Dialogs
 * @param content: The content of the UI.
 */
@Composable
fun DefaultScreenUI(
    stateBase: CommonUiState,
    events: (LoginEvent) -> Unit,
    networkState: NetworkState = NetworkState.Good,
    onTryAgain: () -> Unit = {},
    titleToolbar: String? = null,
    startIconToolbar: ImageVector? = null,
    endIconToolbar: ImageVector? = null,
    onClickStartIconToolbar: () -> Unit = {},
    onClickEndIconToolbar: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            if (titleToolbar != null) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (startIconToolbar != null) {
                        CircleButton(
                            imageVector = startIconToolbar,
                            onClick = { onClickStartIconToolbar() })
                    } else {
                        Spacer_16dp()
                    }
                    Text(titleToolbar, style = MaterialTheme.typography.titleLarge)

                    if (endIconToolbar != null) {
                        CircleButton(
                            imageVector = endIconToolbar,
                            onClick = { onClickEndIconToolbar() })
                    } else {
                        Spacer_16dp()
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            content()
            // process the queue
            if (!stateBase.errorQueue.isEmpty()) {
                stateBase.errorQueue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.Dialog) {
                        CreateUIComponentDialog(
                            title = uiComponent.title,
                            description = uiComponent.message,
                            onRemoveHeadFromQueue = { events(LoginEvent.OnRemoveHeadFromQueue) }
                        )
                    }
                    if (uiComponent is UIComponent.ToastSimple) {
                        ShowSnackbar(
                            title = uiComponent.title,
                            snackbarVisibleState = true,
                            onDismiss = { events(LoginEvent.OnRemoveHeadFromQueue) },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                    if (uiComponent is UIComponent.DialogSimple) {
                        uiComponent.description.Alert(uiComponent.title) {
                            events(LoginEvent.OnRemoveHeadFromQueue)
                        }
                    }
                }
            }

            when(stateBase.progressBarState){
                ProgressBarState.LoadingWithLogo ->{
                    LoadingWithLogoScreen()
                }
                ProgressBarState.ProgressAlertLoading -> {
                    "正在登录...".ProgressAlert(5000L){
                        events(LoginEvent.OnLoading(ProgressBarState.Idle))
                    }
                }
                ProgressBarState.ScreenLoading,ProgressBarState.FullScreenLoading -> {
                    CircularProgressIndicator()
                }
                ProgressBarState.Idle -> {
                    if (networkState == NetworkState.Failed){
                        FailedNetworkScreen(onTryAgain = onTryAgain)
                    }

                }
            }

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FailedNetworkScreen(onTryAgain: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painterResource(Res.drawable.no_wifi), null)
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = "You are currently offline, please reconnect and try again.",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))

        DefaultButton(
            text = "Try Again",
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    DEFAULT__BUTTON_SIZE
                )
        ) {
            onTryAgain()
        }


    }

}

@Composable
fun LoadingWithLogoScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo), // Replace with your WebP image resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}













