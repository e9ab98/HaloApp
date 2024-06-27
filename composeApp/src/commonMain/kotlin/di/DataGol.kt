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
@Serializable
data class LoginBean(
    val detail: String,
    val instance: String,
    val requestId: String,
    val status: Int,
    val timestamp: String,
    val title: String,
    val type: String,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
    val twoFactorAuthEnabled: Boolean,
    val username: String
)
@Serializable
data class Authority(
    val authority: String
)