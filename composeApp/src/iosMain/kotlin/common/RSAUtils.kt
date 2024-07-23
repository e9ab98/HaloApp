package common

actual object RSAUtils {
    actual fun encryptData(str: String, key: String): String {
        return ""
//        return RSACrypto.encryptString(str, publicKey = key)
//            ?: throw RuntimeException("Encryption failed")
    }
}