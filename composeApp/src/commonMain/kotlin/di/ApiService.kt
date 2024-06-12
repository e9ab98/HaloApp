package di

import core.ViewState


interface ApiService {
    companion object {
        const val LOGIN = "login"
        const val Public_Key = "/login/public-key"

    }
    /**
     * 获取 Public-Key
     */

    suspend fun getPublicKey(map: Map<String,Any>): ViewState<PublicKey>
    suspend fun login(csrf: String,username: String,password: String): Any
}