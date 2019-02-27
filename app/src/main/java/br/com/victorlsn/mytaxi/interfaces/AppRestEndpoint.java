package br.com.victorlsn.mytaxi.interfaces;

import java.util.Map;

import br.com.victorlsn.mytaxi.beans.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by victorlsn on 26/02/19.
 */

public interface AppRestEndpoint {
    @Headers("Content-Type: application/json")
    @GET("/")
    Call<Response> getVehiclesList(@QueryMap Map<String, String> params);
}
