package di

import core.ViewState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

class ApiServiceImpl ( private val httpClient: HttpClient):ApiService{

    override suspend fun getPublicKey(map: Map<String, Any>): ViewState<PublicKey> {
        return httpClient.get {
            url {
                takeFrom(map.get("baseUrl") as String)
                encodedPath += ApiService.Public_Key
            }
            contentType(ContentType.Application.Json)
            setBody(map)
        }.body()
    }

    override suspend fun login(csrf: String, username: String, password: String): Any {
        return httpClient.get {

        }.body()
    }

}