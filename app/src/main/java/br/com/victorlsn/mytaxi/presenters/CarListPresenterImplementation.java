package br.com.victorlsn.mytaxi.presenters;

import java.util.HashMap;
import java.util.Map;

import br.com.victorlsn.mytaxi.interfaces.BaseMVP;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import br.com.victorlsn.mytaxi.models.CarListModelImplementation;

/**
 * Created by victorlsn on 26/02/19.
 */

public class CarListPresenterImplementation implements CarListMVP.Presenter {
    private CarListMVP.Model model;
    private CarListMVP.View view;

    public CarListPresenterImplementation() {
        this.model = new CarListModelImplementation(this);
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
    public void requestVehiclesSuccessfully() {

    }

    @Override
    public void requestVehiclesFailure() {

    }

    @Override
    public boolean attachView(BaseMVP.View view) {
        if (view == null) return false;
        this.view = (CarListMVP.View) view;
        return true;
    }
}
