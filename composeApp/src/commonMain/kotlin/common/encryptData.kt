@file:OptIn(ExperimentalEncodingApi::class)

package common

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.DelicateCryptographyApi
import dev.whyoleg.cryptography.algorithms.asymmetric.RSA
import dev.whyoleg.cryptography.algorithms.digest.SHA1
import dev.whyoleg.cryptography.algorithms.digest.SHA256
import io.ktor.utils.io.core.toByteArray
import okio.ByteString.Companion.toByteString
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

//@OptIn(ExperimentalEncodingApi::class)
//suspend fun encryptData(data: String,rsaPublicKey:String): String {
//    val key = loadPublicKey(rsaPublicKey)
//    val encryptOut = key.encrypt(data.toByteArray())
//    return Base64.UrlSafe.encode(encryptOut)
//}
//
//@OptIn(ExperimentalEncodingApi::class)
//private suspend fun loadPublicKey(rsaPublicKey: String): RSA.PKCS1.PublicKey {
//    val encodedKey: ByteArray = Base64.decode(rsaPublicKey)
//    println("loadPublicKey${encodedKey.toByteString()}")
//    return CryptographyProvider.Default.get(RSA.PKCS1)
//        .publicKeyDecoder(SHA256)
//        .decodeFrom(RSA.PublicKey.Format.DER, encodedKey)
//}
