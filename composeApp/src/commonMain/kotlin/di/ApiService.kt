package di


interface ApiService {
    companion object {
        const val LOGIN = "/login"
        const val Public_Key = LOGIN+"/public-key"

    }
    /**
     * 获取 Public-Key
     */

    suspend fun getPublicKey(map: Map<String,Any>): PublicKey

    suspend fun getLogin(map: Map<String,String>, username: String, password: String,remember_me :Boolean): LoginBean
}