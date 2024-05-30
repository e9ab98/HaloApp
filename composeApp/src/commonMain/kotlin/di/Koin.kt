package di

import common.Context
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ui.login.LoginViewModel

fun appModule(context: Context) = module {
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    factory { LoginViewModel() }
}