package common


import kotlin.random.Random

/**
 * 获取指定长度随机字符
 * @param length 字符长度
 * @return String
 */
fun getRandomChat(length: Int): String {
    val str = "abcdefghijklmnopqrstuvwxyz0123456789"
    val result = StringBuilder()
    for (i in 0 until length) {
        result.append(str[Random.nextInt(0, 36)])
    }

    return result.toString()
}

fun randomUUID(): String {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(Regex("[xy]")) { matchResult ->
        val c = matchResult.value[0]
        val r = Random.nextInt(16)
        val v = if (c == 'x') r else (r and 0x3) or 0x8
        v.toString(16)
    }
}



/**
 * 判断一段文本是否是网址或者 IP 并格式化
 * @return 如果网址正确，返回格式化（如果需要）后的网址
 * 否则返回空文本
 */
fun String.formatURL(): String {
    val urlPattern = "^(https?://)([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$"
    val ipPortPattern = "^(https?://)?((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]" +
            "|2[0-4][0-9]|[01]?[0-9][0-9]?)((:(?:[1-9]\\d{0,3}|[1-5]\\d{4}|6[0-5][0-5][0-3][0-5]))?)$"
    if (this.matches(Regex(urlPattern))) {
        return this
    }

    // 当前 URL 是 IP 地址
    if (this.matches(Regex(ipPortPattern))) {
        return if (this.contains("http")) {
            this
        } else {
            "http://$this"
        }
    }

    return ""
}

/**
 * 获取 [startString] 和 [endString] 中间的字符串
 * @param startString 开始字符串，为空则代表从头开始
 * @param endString 结束字符串（从开始字符串后匹配到的第一个字符串后截止）
 *                  为空则代表到字符串最后
 * @return 返回中间字符串，匹配失败返回空文本
 */
fun String.sub(startString: String, endString: String): String {
    if (this.isEmpty()) {
        return ""
    }

    var startIndex = if (startString.isEmpty()) 0 else this.indexOf(startString)
    if (startIndex == -1) {
        // 开始字符串匹配失败
        return ""
    }

    // 把开始截取的索引放到开始字符之后
    startIndex += startString.length

    val endIndex = if (endString.isEmpty()) this.length else this.indexOf(endString, startIndex)
    if (endIndex == -1) {
        // 结束字符串匹配失败，直接从开始位置截取到文本最后
        return this.substring(startIndex, this.lastIndex)
    }

    return this.substring(startIndex, endIndex)
}


