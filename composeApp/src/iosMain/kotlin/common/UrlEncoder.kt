package common

import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.NSCharacterSet
import platform.Foundation.NSString
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding

actual object UrlEncoder {
    actual fun encode(value: String): String {
        return (value as NSString).stringByAddingPercentEncodingWithAllowedCharacters(
            NSCharacterSet.URLQueryAllowedCharacterSet
        ) ?: value
    }

    actual fun decode(value: String): String {
        return (value as NSString).stringByRemovingPercentEncoding() ?: value
    }
}

