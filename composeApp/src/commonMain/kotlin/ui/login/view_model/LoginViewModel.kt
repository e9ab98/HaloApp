package ui.login.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import common.BaseViewModel
import common.getRandomChat
import common.randomUUID
import core.AppDataStore
import core.ConfigKey
import core.ProgressBarState
import core.ViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ui.login.LoginState

class LoginViewModel(private val loginRepository: LoginRepository,private val appDataStoreManager: AppDataStore) : BaseViewModel(){
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

    fun getPublicKey(event: LoginEvent.Login) {
        val map = mutableMapOf<String,String>()
        map["p_uv_id"] = getRandomChat(16)
        map["XSRF-TOKEN"] = randomUUID()
        loginRepository.getPublicKey(map).onEach {
            when(it){
                is ViewState.Loading ->{
//                    println("ViewState.Loading")
//                    onTriggerEvent(LoginEvent.Error(uiComponent = UIComponent.Dialog()))
                    println("ViewState.Loading:"+it.isShow)
                    if (it.isShow){
                        setProgressBarState(ProgressBarState.ProgressAlertLoading)
                    }
                }
                is ViewState.Success ->{
                    println("ViewState.Success:"+it.data)
                }
                is ViewState.Error -> {
                    println("ViewState.Error:"+it.message)
                }
                is ViewState.onEmpty -> {
                    println("ViewState.onEmpty:"+it.message)
                }
                is ViewState.SuccessRestful -> {
                    println("ViewState.SuccessRestful:"+it.data)
                }

            }
        }.launchIn(viewModelScope)

    }

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    appDataStoreManager.setToRoom(ConfigKey.HALO_URL,state.value.urlLogin)
                    appDataStoreManager.setToRoom(ConfigKey.USERNAME,state.value.usernameLogin)
                    appDataStoreManager.setToRoom(ConfigKey.PASSWORD,state.value.passwordLogin)
                }
                getPublicKey(event)

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
                removeHeadMessage()
            }

            is LoginEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is LoginEvent.OnRetryNetwork -> {

            }

            is LoginEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }

        }
    }
}