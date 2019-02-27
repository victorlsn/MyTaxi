package br.com.victorlsn.mytaxi.models;

import java.util.List;
import java.util.Map;

import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.data.Controller;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by victorlsn on 26/02/19.
 */


public class CarListModelImp implements CarListMVP.Model {
    private CarListMVP.Presenter presenter;

    public CarListModelImp(CarListMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getVehicles(Map<String, String> coordinates) {
        Controller.getInstance().doApiCall().getVehiclesList(coordinates).enqueue(new Callback<br.com.victorlsn.mytaxi.beans.Response>() {
            @Override
            public void onResponse(Call<br.com.victorlsn.mytaxi.beans.Response> call, Response<br.com.victorlsn.mytaxi.beans.Response> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<Car> carList = response.body().getCars();

                    presenter.requestVehiclesSuccessfully(carList);
                }
            }

            @Override
            public void onFailure(Call<br.com.victorlsn.mytaxi.beans.Response> call, Throwable t) {
                presenter.requestVehiclesFailure(t.getMessage());
            }
        });
    }
}
