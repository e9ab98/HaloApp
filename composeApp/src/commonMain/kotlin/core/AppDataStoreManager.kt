package core

import common.Context
import common.getAllData
import common.getData
import common.putData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch


const val APP_DATASTORE = "halo_app"

class AppDataStoreManager(val context: Context) : AppDataStore {
    init {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            // 在类初始化时从数据库中读取所有数据存到缓存中
            val list = readAllValue()
            list.forEach {
                caches[it.key] = it.key
            }
        }
    }
    /**
     * 将配置表中的配置信息缓存
     */
    private val caches = mutableMapOf<String, String>()

    override suspend fun setValue(
        key: String,
        value: String
    ) {
        context.putData(key, value)
    }

    override suspend fun readValue(
        key: String,
    ): String? {
        return context.getData(key)
    }

    override suspend fun readAllValue(): Map<String, String?> {
        return context.getAllData()
    }


    /**
     * 将数据缓存到集合缓存中
     */
    override fun cache(key: ConfigKey, value: String) {
        caches[key.key] = value
    }

    /**
     * 从缓存中获取配置
     * @return 不存在返回空文本
     */
    override fun get(key: ConfigKey): String {
        return caches[key.key] ?: ""
    }

    /**
     * 从 Room 数据库中获取配置
     * @param key 键
     */
    override suspend fun getByRoom(key: ConfigKey): String {
        var keyValue = readValue(key.key)
        // 从数据库获取数据的同时也写入缓存
        if (keyValue != null) {
            cache(key, keyValue)
        }else{
            keyValue = ""
        }
        return keyValue
    }

    /**
     * 修改配置
     * 同时保存到缓存和数据库
     * @param key 键
     * @param value 值
     */
    override suspend fun setToRoom(key: ConfigKey, value: String) {
        setValue(key.key, value)
        // 同时将配置保存到缓存中
        cache(key, value)
    }

    /**
     * 修改配置
     * 只保存到缓存中
     * @param key 键
     * @param value 值
     */
    override fun set(key: ConfigKey, value: String) {
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