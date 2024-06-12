package core

import coil3.network.HttpException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.http.parsing.ParseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import okio.IOException

@Serializable
data class BaseResult<T>(var `data`: T,var code: Int,var message: String,var page: String,var success: Boolean)


sealed class ViewState<ResultType> {

    /**
     * Describes success state of the UI with
     * [data] shown
     */
    data class SuccessRestful<ResultType>(val data: BaseResult<ResultType>) : ViewState<ResultType>()
    data class Success<ResultType>(val data: ResultType) : ViewState<ResultType>()


    data class onEmpty<ResultType>(val message: String = "") : ViewState<ResultType>()

    class OnResult<ResultType>: ViewState<ResultType>()

    /**
     * Describes loading state of the UI
     */
    class Loading<ResultType> : ViewState<ResultType>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return true
        }


    }

    /**
     *  Describes error state of the UI
     */
    @Serializable
    data class Error<ResultType>(val message: String = "") : ViewState<ResultType>()

    companion object {
        /**
         * Creates [ViewState] object with [SuccessRestful] state and [data].
         */
        fun <ResultType> SuccessRestful(data: BaseResult<ResultType>): ViewState<ResultType> {
            if (data !=null){
                if (data.success){
                    return SuccessRestful(data)
                }
                return Error(data.message)
            }else{
                return onEmpty("暂无数据！")
            }
        }
        /**
         * Creates [ViewState] object with [SuccessRestful] state and [data].
         */
        fun <ResultType> success(data: ResultType): ViewState<ResultType> {
            return if (data != null) {
                Success(data)
            } else {
                onEmpty("暂无数据！")
            }
        }

        /**
         * Creates [ViewState] object with [Loading] state to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(): ViewState<ResultType> = Loading()
        fun <ResultType> onResult(): ViewState<ResultType> = OnResult()
        /**
         * Creates [ViewState] object with [Error] state and [message].
         */
        fun <ResultType> error(e: Throwable): ViewState<ResultType> {
            val errMessage: String
                errMessage = if (e is HttpException) {
                    try {
                        e.response.toString()
                    } catch (e1: IOException) {
                        e1.printStackTrace()
                        e1.message!!
                    }
                } else if (e is SocketTimeoutException  || e is ConnectTimeoutException) {
                   "网络连接超时，请检查您的网络状态，稍后重试！"
                } else if (e is NullPointerException) {
                    "空指针异常"
                }else if (e is ClassCastException) {
                    "类型转换错误"
                } else if (e is JsonConvertException
                    || e is SerializationException
                    || e is ParseException
                ) {
                    "解析错误"
                } else if (e is IllegalStateException) {
                    e.message!!
                } else if (e is IllegalArgumentException) {
                    e.message!!
                } else {
                    "未知错误,请联系开发者！"
                }
            return Error(errMessage)
        }


        fun <ResultType> initApiResponse(data: BaseResult<ResultType>): ResultType = ApiResponse(data)

        fun <ResultType> ApiResponse(data: BaseResult<ResultType>) : ResultType{
            return when(data.code){
                200 -> return data.data
                else -> return data.data
            }
        }


    }



}