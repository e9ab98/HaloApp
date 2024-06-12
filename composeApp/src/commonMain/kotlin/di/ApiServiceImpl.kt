package di

import core.AppDataStoreManager
import core.AppDataUtils
import core.BaseResult
import core.ViewState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
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

class ApiServiceImpl ( private val httpClient: HttpClient):ApiService{

    override suspend fun getPublicKey(map: Map<String, Any>): PublicKey {
        val cookieString = map.entries.joinToString("; ") { "${it.key}=${it.value}" }
        return httpClient.get {
            url {
                headers {
                    append(HttpHeaders.Cookie, "browsertc=1; $cookieString")
                }
                takeFrom(AppDataUtils.getBaseUrl())
                encodedPath += ApiService.Public_Key
            }
            contentType(ContentType.Application.Json)

        }.body()
    }

    override suspend fun login(csrf: String, username: String, password: String): Any {
        return httpClient.get {

        }.body()
    }

}