package core


interface AppDataStore {

    suspend fun setValue(key: String,value: String)

    suspend fun readValue(key: String): String?
    suspend fun readAllValue(): Map<String, String?>
    /**
     * 将数据缓存到集合缓存中
     */
    fun cache(key: ConfigKey, value: String)
    /**
     * 修改配置
     * 只保存到缓存中
     * @param key 键
     * @param value 值
     */
    fun set(key: ConfigKey, value: String)
    /**
     * 从缓存中获取配置
     * @return 不存在返回空文本
     */
    fun get(key: ConfigKey): String
    /**
     * 从 Room 数据库中获取配置
     * @param key 键
     */
    suspend fun getByRoom(key: ConfigKey): String
    /**
     * 修改配置
     * 同时保存到缓存和数据库
     * @param key 键
     * @param value 值
     */
    suspend fun setToRoom(key: ConfigKey, value: String)


}

