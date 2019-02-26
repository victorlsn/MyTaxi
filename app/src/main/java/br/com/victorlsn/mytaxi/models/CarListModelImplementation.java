package br.com.victorlsn.mytaxi.models;

import org.json.JSONObject;

import java.util.Map;

import br.com.victorlsn.mytaxi.data.Controller;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by victorlsn on 26/02/19.
 */

public class CarListModelImplementation implements CarListMVP.Model {
    private CarListMVP.Presenter presenter;

    public CarListModelImplementation(CarListMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getVehicles(Map<String, String> coordinates) {
        Controller.getInstance().doApiCall().getVehiclesList(coordinates).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject json = response.body();
                String teste = "";
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
