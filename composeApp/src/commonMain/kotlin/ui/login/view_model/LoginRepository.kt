package ui.login.view_model


import core.ViewState
import di.ApiService
import di.LoginBean
import di.PublicKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginRepository(
    private val service: ApiService,
) {

    fun getPublicKey(map: Map<String,Any>): Flow<ViewState<PublicKey>>{
        return flow {
            emit(ViewState.loading(true))
            val apiResponse = service.getPublicKey(map)
            emit(ViewState.success(apiResponse))
            emit(ViewState.loading(false))
        }.catch {
            emit(ViewState.loading(false))
            emit(ViewState.error(it))
        }

    }

    fun getLogin(map: Map<String,String>, username: String, password: String,remember_me :Boolean): Flow<ViewState<LoginBean>>{
        return flow {
            emit(ViewState.loading(true))
            val apiResponse = service.getLogin(map, username, password,remember_me)
            emit(ViewState.success(apiResponse))
            emit(ViewState.loading(false))
        }.catch {
            emit(ViewState.loading(false))
            emit(ViewState.error(it))
        }

    }


}

