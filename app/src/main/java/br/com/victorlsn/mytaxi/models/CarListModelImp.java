package br.com.victorlsn.mytaxi.models;

import android.content.Context;
import android.support.annotation.NonNull;

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
 *
 */


public class CarListModelImp implements CarListMVP.Model {
    private CarListMVP.Presenter presenter;

    public CarListModelImp(CarListMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getVehicles(Context context, Map<String, String> coordinates) {
        try {
            Controller.getInstance().doApiCall(context).getVehiclesList(coordinates).enqueue(new Callback<br.com.victorlsn.mytaxi.beans.Response>() {
                @Override
                public void onResponse(@NonNull Call<br.com.victorlsn.mytaxi.beans.Response> call, @NonNull Response<br.com.victorlsn.mytaxi.beans.Response> response) {
                    if (response.code() == 200 && response.body() != null) {
                        List<Car> carList = response.body().getCars();

                        presenter.requestVehiclesSuccessfully(carList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<br.com.victorlsn.mytaxi.beans.Response> call, @NonNull Throwable t) {
                    presenter.requestVehiclesFailure(t.getMessage());
                }
            });
        }
        catch (Exception e) {
            presenter.requestVehiclesFailure(null);
        }
    }
}
