package br.com.victorlsn.mytaxi.interfaces;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

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
    Call<JSONObject> getVehiclesList(@QueryMap Map<String, String> params);
}
