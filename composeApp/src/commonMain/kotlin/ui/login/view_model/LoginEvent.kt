package ui.login.view_model

import core.UIComponent
import core.NetworkState
import core.ProgressBarState

sealed class LoginEvent {


    data class OnUpdateNameRegister(val value: String) : LoginEvent()
    data class OnUpdateUrlLogin(val value: String) : LoginEvent()
    data class OnUpdateUsernameLogin(val value: String) : LoginEvent()
    data class OnUpdatePasswordLogin(val value: String) : LoginEvent()

    data object Register : LoginEvent()
    data object Login : LoginEvent()
    data object OnRemoveHeadFromQueue : LoginEvent()

    data class Error(val uiComponent: UIComponent) : LoginEvent()


    data object OnRetryNetwork : LoginEvent()

    data class OnUpdateNetworkState(val networkState: NetworkState) : LoginEvent()

    data class OnLoading(val progressBarState: ProgressBarState) : LoginEvent()

}
