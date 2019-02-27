package br.com.victorlsn.mytaxi.presenters;

import android.util.Log;

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

    public void requestVehicles() {
        try {
            view.showProgressBar(true, null);

            Map<String, String> coordinates = new HashMap<String, String>();
            coordinates.put("p1Lat", "53.694865");
            coordinates.put("p1Lon", "9.757589");
            coordinates.put("p2Lat", "53.394655");
            coordinates.put("p2Lon", "10.099891");


            model.getVehicles(coordinates);
        }
        catch (Exception e) {

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
        // TODO showErrorToast
        view.showProgressBar(false, null);
        view.receiveVehiclesList(null);
    }

    @Override
    public boolean attachView(BaseMVP.View view) {
        if (view == null) return false;
        this.view = (CarListMVP.View) view;
        return true;
    }
}
