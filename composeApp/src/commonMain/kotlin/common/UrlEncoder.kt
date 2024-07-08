package common

expect object UrlEncoder {
    fun encode(value: String): String
    fun decode(value: String): String
}