package ui.login.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ui.login.LoginState

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
            is LoginEvent.OnUpdateUrlLogin -> {
                state.value = state.value.copy(urlLogin = event.value)
            }
            is LoginEvent.OnUpdatePasswordLogin -> {
                state.value = state.value.copy(passwordLogin = event.value)
            }

            is LoginEvent.OnUpdateUsernameLogin -> {
                state.value = state.value.copy(usernameLogin = event.value)
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