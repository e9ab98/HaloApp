package di

import core.AppDataStoreManager
import core.AppDataUtils
import core.BaseResult
import core.ViewState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.cookies
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.util.InternalAPI
import org.koin.core.component.getScopeName

class ApiServiceImpl ( private val httpClient: HttpClient):ApiService{

    override suspend fun getPublicKey(map: Map<String, Any>): PublicKey {

        val cookieString = map.entries.joinToString("; ") { "${it.key}=${it.value}" }
        return httpClient.get(AppDataUtils.getBaseUrl()+ApiService.Public_Key) {
            headers {
                append(HttpHeaders.Cookie, "browsertc=1; $cookieString")
                append(HttpHeaders.Cookie, "browsertc=1; $cookieString")
            }
            contentType(ContentType.Application.Json)
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun getLogin(map: Map<String,String>, username: String, password: String,remember_me :Boolean): LoginBean {
                return httpClient.post(AppDataUtils.getBaseUrl()) {
                    headers {
                        append(HttpHeaders.Cookie, "p_uv_id="+map["p_uv_id"])
                        append(HttpHeaders.Cookie, "XSRF-TOKEN="+map["XSRF-TOKEN"])
                        append("x-xsrf-token", map["XSRF-TOKEN"].toString())
                        append("sec-ch-ua-mobile", "?1")
                        append(HttpHeaders.UserAgent, "Mozilla/5.0 (Linux; Android 14; Redmi K20 Pro Build/AP1A.240505.005) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/125.0.6422.113 Mobile Safari/537.36")
                        append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                        append(HttpHeaders.Accept, "application/json, text/plain, */*")
                        append("x-requested-with", "XMLHttpRequest")
                        append("sec-ch-ua-platform", "\"Android\"")
                        append("sec-fetch-site", "same-origin")
                        append("sec-fetch-mode", "cors")
                        append("sec-fetch-dest", "empty")
                        append(HttpHeaders.AcceptLanguage, "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                        append("priority", "u=1, i")
                        append(HttpHeaders.Pragma, "no-cache")
                        append(HttpHeaders.CacheControl, "no-cache")
            }
            url {
                encodedPath += ApiService.LOGIN
                parameter("remember-me",remember_me)
            }
            val csrf = map["XSRF-TOKEN"]
            body = "_csrf=$csrf&username=$username&password=$password"

        }.body()
    }

}