package common


expect suspend fun Context.putData(key: String, `object`: String)

expect suspend fun Context.getData(key: String): String?


expect suspend fun Context.getAllData(): Map<String, String?>