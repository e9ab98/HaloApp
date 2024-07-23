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
    val detail: String = "",
    val instance: String = "",
    val requestId: String = "",
    val status: Int = 0,
    val timestamp: String = "",
    val title: String = "",
    val type: String = "",
    val accountNonExpired: Boolean = true,
    val accountNonLocked: Boolean = true,
    val authorities: List<Authority> = emptyList(),
    val credentialsNonExpired: Boolean = true,
    val enabled: Boolean = true,
    val twoFactorAuthEnabled: Boolean = false,
    val username: String = ""
)
@Serializable
data class Authority(
    val authority: String
)