package common

expect object RSAUtils {
    fun encryptData(str: String, key: String): String
}