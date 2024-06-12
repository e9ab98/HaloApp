package core

object AppDataUtils {
    fun getBaseUrl(): String {
        return get(ConfigKey.HALO_URL)
    }

    /**
     * 将配置表中的配置信息缓存
     */
    val caches = mutableMapOf<String, String>()

    /**
     * 将数据缓存到集合缓存中
     */
    fun cache(key: ConfigKey, value: String) {
        caches[key.key] = value
    }

    /**
     * 从缓存中获取配置
     * @return 不存在返回空文本
     */
    fun get(key: ConfigKey): String {
        return caches[key.key] ?: ""
    }
    /**
     * 修改配置
     * 只保存到缓存中
     * @param key 键
     * @param value 值
     */
     fun set(key: ConfigKey, value: String) {
        cache(key, value)
    }
}


/**
 * 配置信息枚举类
 */
enum class ConfigKey(val key: String) {
    // Halo 站点地址
    HALO_URL("halo_url"),
    // 用户名
    USERNAME("username"),
    // 密码
    PASSWORD("password"),
    // 登录 SESSION TOKEN
    SESSION_TOKEN("session_token")
}