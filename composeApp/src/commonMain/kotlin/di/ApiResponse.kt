package di

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/******************************* DATA ****************************************/
/**
 * Public-Key 实体类
 * @param base64Format Public-Key
 */
data class PublicKey(val base64Format: String)