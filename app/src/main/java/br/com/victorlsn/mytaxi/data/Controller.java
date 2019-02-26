package br.com.victorlsn.mytaxi.data;

import java.util.concurrent.TimeUnit;

import br.com.victorlsn.mytaxi.interfaces.AppRestEndpoint;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by victorlsn on 26/02/19.
 */

public class Controller {

    private static Controller instance = null;
    private AppRestEndpoint apiCall;

    private Controller() {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);

        OkHttpClient client = okHttpBuilder.build();

        String BASE_URL = "https://fake-poi-api.mytaxi.com/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiCall = retrofit.create(AppRestEndpoint.class);
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public AppRestEndpoint doApiCall() {
        return apiCall;
    }
}

