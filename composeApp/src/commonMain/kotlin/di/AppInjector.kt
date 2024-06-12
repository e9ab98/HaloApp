package di

import common.Context
import core.AppDataStore
import core.AppDataStoreManager
import core.KtorHttpClient
import kotlinx.serialization.json.Json

import org.koin.core.KoinApplication
import org.koin.dsl.module
import ui.login.view_model.LoginRepository
import ui.login.view_model.LoginViewModel

object AppInjector {
    fun injector(context: Context): KoinApplication.() -> Unit = {
        modules(appModule(context))
    }
    fun appModule(context: Context) = module {
        single { Json { isLenient = true; ignoreUnknownKeys = true } }
        single {
            KtorHttpClient.httpClient()
        }

        single<AppDataStore> { AppDataStoreManager(context) }
        single { LoginRepository(get()) }
        single<ApiService> {
            ApiServiceImpl(get())
        }
        factory { LoginViewModel(get(),get()) }
    }




}