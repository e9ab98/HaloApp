package ui.login.view_model


import core.AppDataStore
import core.DataState
import core.ProgressBarState
import di.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginInteractor(
    private val service: ApiService,
    private val appDataStoreManager: AppDataStore,
) {

}