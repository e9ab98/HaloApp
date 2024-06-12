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
                AppDataUtils.caches[it.key] = it.key
            }
        }
    }


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
     * 从 Room 数据库中获取配置
     * @param key 键
     */
    override suspend fun getByRoom(key: ConfigKey): String {
        var keyValue = readValue(key.key)
        // 从数据库获取数据的同时也写入缓存
        if (keyValue != null) {
            AppDataUtils.cache(key, keyValue)
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
        AppDataUtils.cache(key, value)
    }


}
