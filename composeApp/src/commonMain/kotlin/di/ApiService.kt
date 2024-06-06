package di


interface ApiService {
    companion object {
        const val LOGIN = "login"

        const val publicKey = "/login/public-key"
    }

    suspend fun getPublicKey(): PublicKey

}