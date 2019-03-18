package br.com.victorlsn.mytaxi.data

import android.content.Context
import android.widget.Toast

import java.util.concurrent.TimeUnit

import br.com.victorlsn.mytaxi.R
import br.com.victorlsn.mytaxi.interfaces.AppRestEndpoint
import br.com.victorlsn.mytaxi.util.AppTools
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by victorlsn on 26/02/2019
 *
 */

class Controller private constructor() {
    private val apiCall: AppRestEndpoint

    init {

        val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)

        val client = okHttpBuilder.build()


        val BASE_URL = "https://fake-poi-api.mytaxi.com/"
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        apiCall = retrofit.create(AppRestEndpoint::class.java)
    }

    fun doApiCall(context: Context): AppRestEndpoint? {
        if (AppTools.isOnline(context)) {
            return apiCall
        } else {
            AppTools.showToast(context, context.getString(R.string.connection_error), Toast.LENGTH_LONG)
            return null
        }
    }

    companion object {

        @Volatile private var INSTANCE : Controller ? = null

        fun getInstance(): Controller {
            return INSTANCE?: synchronized(this){
                Controller().also {
                    INSTANCE = it
                }
            }
        }
    }
}

