package di

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/******************************* DATA ****************************************/
/**
 * Public-Key 实体类
 * @param base64Format Public-Key
 */
@Serializable
data class PublicKey(@SerialName("base64Format") val base64Format: String)