package common

import kotlinx.coroutines.flow.MutableSharedFlow
import platform.Foundation.NSUserDefaults


actual suspend fun Context.putData(key: String, `object`: String) {
    val sharedFlow = MutableSharedFlow<String>()
    NSUserDefaults.standardUserDefaults().setObject(`object`, key)
    sharedFlow.emit(`object`)
}

actual suspend inline fun Context.getData(key: String): String? {
    return NSUserDefaults.standardUserDefaults().stringForKey(key)
}
actual suspend fun Context.getAllData(): Map<String, String?> {
    val userDefaults = NSUserDefaults.standardUserDefaults()
    val dictionary = userDefaults.dictionaryRepresentation()
    val allData = mutableMapOf<String, String?>()

    dictionary.keys.forEach { key ->
        allData[key as String] = dictionary[key] as? String
    }

    return allData
}