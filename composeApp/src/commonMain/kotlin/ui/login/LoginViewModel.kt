package ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ui.splash.view_model.LoginEvent

class LoginViewModel : ViewModel(){
    val state: MutableState<LoginState> = mutableStateOf(LoginState())


    fun onTriggerEvent(event: LoginEvent) {
        when (event) {

            is LoginEvent.Login -> {

            }

            is LoginEvent.Register -> {

            }

            is LoginEvent.OnUpdateNameRegister -> {

            }

            is LoginEvent.OnUpdatePasswordLogin -> {

            }

            is LoginEvent.OnUpdateUsernameLogin -> {

            }

            is LoginEvent.OnRemoveHeadFromQueue -> {

            }

            is LoginEvent.Error -> {

            }

            is LoginEvent.OnRetryNetwork -> {

            }

            is LoginEvent.OnUpdateNetworkState -> {

            }
        }
    }
}