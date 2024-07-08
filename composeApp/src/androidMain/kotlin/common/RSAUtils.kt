package common

import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import android.util.Base64;
import javax.crypto.Cipher
import java.net.URLEncoder


actual object RSAUtils {
    actual fun encryptData(str: String, key: String): String {
        // 将文本格式的公钥解码为 PublicKey 对象
        val keyBytes = Base64.decode(key,Base64.NO_WRAP)
        val pubKeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val pubKey = keyFactory.generatePublic(pubKeySpec)

        // 使用公钥对文本加密
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        val encryptedBytes = cipher.doFinal(str.toByteArray())
        // NO_WRAP 表示不在结果中加上换行和空格
        return URLEncoder.encode(Base64.encodeToString(encryptedBytes,Base64.NO_WRAP), "UTF-8")
    }
}