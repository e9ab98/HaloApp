package common

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

actual object RSAUtils {
    actual fun encryptData(str: String, key: String): String {
        // 将文本格式的公钥解码为 PublicKey 对象
        val keyBytes = Base64.decode(key, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val keyPublic = keyFactory.generatePublic(keySpec)

        // 使用公钥对文本加密
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keyPublic)
        val encryptedBytes = cipher.doFinal(str.toByteArray())
        // NO_WRAP 表示不在结果中加上换行和空格
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    private fun decodePublicKey(publicKeyBase64: String): PublicKey {
        val keyBytes = Base64.decode(publicKeyBase64, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }
}