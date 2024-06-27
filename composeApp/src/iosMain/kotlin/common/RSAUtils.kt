package common

import kotlinx.cinterop.toKString
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding

actual object RSAUtils {
    actual fun encryptData(str: String, key: String): String {
        return ""
//        return RSACrypto.encryptString(str, publicKey = key)
//            ?: throw RuntimeException("Encryption failed")
    }
}