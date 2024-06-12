package ui.login.view_model


import core.AppDataStore
import core.DataState
import core.ProgressBarState
import core.ViewState
import core.handleUseCaseException
import di.ApiService
import di.PublicKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginRepository(
    private val service: ApiService,
) {

    fun getPublicKey(map: Map<String,Any>): Flow<ViewState<PublicKey>>{
        return flow {
            emit(ViewState.loading())
            val apiResponse = service.getPublicKey(map)
            emit(ViewState.success(apiResponse))
        }.catch {
            emit(ViewState.error(it))
        }

    }


}

