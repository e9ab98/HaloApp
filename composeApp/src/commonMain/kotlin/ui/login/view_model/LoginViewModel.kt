package ui.login.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.randomUUID
import core.AppDataStore
import core.ConfigKey
import core.JAlertResponse
import core.UIComponent
import core.ViewState
import io.ktor.client.plugins.logging.Logger
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ui.login.LoginState

class LoginViewModel(private val loginRepository: LoginRepository,private val appDataStoreManager: AppDataStore) : ViewModel(){
    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    init {
        viewModelScope.launch {
            state.value =
                state.value.copy(urlLogin = appDataStoreManager.getByRoom(ConfigKey.HALO_URL))
            state.value =
                state.value.copy(usernameLogin = appDataStoreManager.getByRoom(ConfigKey.USERNAME))
            state.value =
                state.value.copy(passwordLogin = appDataStoreManager.getByRoom(ConfigKey.PASSWORD))

        }
    }

    fun getPublicKey() {
        val map = mutableMapOf<String,String>()
        map["XSRF-TOKEN"] = randomUUID()
        map["baseUrl"] = state.value.urlLogin
        loginRepository.getPublicKey(map).onEach {
            when(it){
                is ViewState.Loading ->{
                    UIComponent.Toast(JAlertResponse("正在登录", "开发中"))
                    println("ViewState.Loading")
                }
                is ViewState.Success ->{
                    println("ViewState.Success:"+it.data)
                }
                is ViewState.Error -> {
                    println("ViewState.Error:"+it.message)
                }
                is ViewState.OnResult -> {
                    println("ViewState.OnResult:")
                }
                is ViewState.SuccessDefault -> {
                    println("ViewState.SuccessDefault:"+it.data)
                }
                is ViewState.onEmpty -> {
                    println("ViewState.onEmpty:"+it.message)
                }
            }
        }.launchIn(viewModelScope)

    }
    fun setData(): Unit {
        viewModelScope.launch {
            appDataStoreManager.setToRoom(ConfigKey.HALO_URL,state.value.urlLogin)
            appDataStoreManager.setToRoom(ConfigKey.USERNAME,state.value.usernameLogin)
            appDataStoreManager.setToRoom(ConfigKey.PASSWORD,state.value.passwordLogin)

            getPublicKey()
        }
    }
    fun onTriggerEvent(event: LoginEvent) {
        when (event) {

            is LoginEvent.Login -> {
                setData()

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