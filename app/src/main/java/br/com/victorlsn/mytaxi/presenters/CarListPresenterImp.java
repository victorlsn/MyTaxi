package br.com.victorlsn.mytaxi.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.events.CarListPopulatedEvent;
import br.com.victorlsn.mytaxi.interfaces.BaseMVP;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import br.com.victorlsn.mytaxi.models.CarListModelImp;

/**
 * Created by victorlsn on 26/02/19.
 */

public class CarListPresenterImp implements CarListMVP.Presenter {
    private CarListMVP.Model model;
    private CarListMVP.View view;

    public CarListPresenterImp() {
        this.model = new CarListModelImp(this);
    }

    @Override
    public void attachView(BaseMVP.View view) {
        if (view == null) return;
        this.view = (CarListMVP.View) view;
    }

    public void requestVehicles(Context context) {
        try {
            view.showProgressBar(true, "Retrieving information...");

            Map<String, String> coordinates = new HashMap<>();
            coordinates.put("p1Lat", "53.694865");
            coordinates.put("p1Lon", "9.757589");
            coordinates.put("p2Lat", "53.394655");
            coordinates.put("p2Lon", "10.099891");
            model.getVehicles(context, coordinates);
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

    }

    @Override
    public void requestVehiclesSuccessfully(List<Car> cars) {
        view.showProgressBar(false, null);
        view.receiveVehiclesList(cars);
        EventBus.getDefault().post(new CarListPopulatedEvent(cars));

        for (Car car: cars) {
            Log.d("Car: ", car.toString());
        }
    }

    @Override
    public void requestVehiclesFailure(String error) {
        if (error != null) {
            view.showToast(error, Toast.LENGTH_SHORT);
        }
        view.showProgressBar(false, null);
        view.receiveVehiclesList(null);
        EventBus.getDefault().post(new CarListPopulatedEvent(null));
    }
}
