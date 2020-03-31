package com.naez.colivingapp.data.server

import android.util.Log
import com.naez.data.ResultData
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
    call: suspend () -> ResultData<T>,
    errorMessage: String
): ResultData<T> = try {
    call.invoke()
} catch (e: Exception) {
    Log.e("safeApiCall", e.message.toString())
    ResultData.Error(IOException(errorMessage, e))
}

fun <T : Any> Response<T>.callServices(): ResultData<T> {
    if (this.isSuccessful) {
        return if (this.body() != null) {
            ResultData.Success(this.body() as T)
        } else {
            ResultData.Error(Exception(this.errorBody().toString()))
        }
    }
    return ResultData.Error(IOException())
}