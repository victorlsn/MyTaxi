package br.com.victorlsn.mytaxi.data;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.interfaces.AppRestEndpoint;
import br.com.victorlsn.mytaxi.util.AppTools;
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

    public AppRestEndpoint doApiCall(Context context) {
        if (AppTools.isOnline(context)) {
            return apiCall;
        }
        else {
            AppTools.showToast(context, context.getString(R.string.connection_error), Toast.LENGTH_LONG);
            return null;
        }
    }
}

