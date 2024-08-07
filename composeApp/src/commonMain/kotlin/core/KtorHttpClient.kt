package core

import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object KtorHttpClient {
    @OptIn(ExperimentalSerializationApi::class)
    fun httpClient() = HttpClient {
        expectSuccess = false
        install(HttpTimeout) {
            val timeout = 60000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }

        install(ResponseObserver) {
            onResponse { response ->
                println("AppDebug HTTP ResponseObserver status: ${response.status.value}")
            }
        }
        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value
                println("HttpResponseValidator: ${statusCode}")



                /*
                                    when (statusCode) {
                                        in 300..399 -> throw RedirectResponseException(response)
                                        in 400..499 -> throw ClientRequestException(response)
                                        in 500..599 -> throw ServerResponseException(response)
                                    }

                                    if (statusCode >= 600) {
                                        throw ResponseException(response)
                                    }
                                }

                                handleResponseException { cause: Throwable ->
                                    throw cause
                                }*/
            }
        }

        install(Logging) {
            //  logger = Logger.DEFAULT
            level = LogLevel.ALL

            logger = object : Logger {
                override fun log(message: String) {
                    println("AppDebug KtorHttpClient message:$message")
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                explicitNulls = true
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
                encodeDefaults = true
                classDiscriminator = "#class"
            })
        }
        defaultRequest {
            headers {
                append(HttpHeaders.AcceptLanguage, "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
            }
        }
//        engine {
//
//            // 设置代理服务器
//            proxy = ProxyBuilder.http("http://192.168.5.187:8888")
//
//        }
//        engine {
////            https {
////                this.hostnameVerifier = HostnameVerifier.ANY
////            }
//        }


    }

}