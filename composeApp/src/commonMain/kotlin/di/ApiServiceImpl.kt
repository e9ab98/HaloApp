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
                append(HttpHeaders.Cookie, "p_uv_id="+map["p_uv_id"])
                append(HttpHeaders.Cookie, "XSRF-TOKEN="+map["XSRF-TOKEN"])
                append("X-XSRF-TOKEN", map["XSRF-TOKEN"].toString())
                 append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                append(HttpHeaders.Accept, "application/json, text/plain, */*")
            }
            contentType(ContentType.Application.Json)
        }.body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun getLogin(map: Map<String,String>, username: String, password: String,remember_me :Boolean): LoginBean {
                return httpClient.post(AppDataUtils.getBaseUrl()) {
                    headers {
                        append("X-XSRF-TOKEN", map["XSRF-TOKEN"].toString())
                        append("X-Requested-With", "XMLHttpRequest")
                        append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                        append(HttpHeaders.Cookie, "XSRF-TOKEN="+map["XSRF-TOKEN"])
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