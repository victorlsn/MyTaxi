package br.com.victorlsn.mytaxi.interfaces;

import java.util.List;
import java.util.Map;

import br.com.victorlsn.mytaxi.beans.Car;

/**
 * Created by victorlsn on 26/02/19.
 */

public interface CarListMVP {
    interface Model {
        void getVehicles(Map<String, String> coordinates);
    }

    interface Presenter extends BaseMVP.Presenter {
        void requestVehicles();

        void requestVehiclesSuccessfully(List<Car> cars);

        void requestVehiclesFailure(String error);
    }

    interface View extends BaseMVP.View {
        void receiveVehiclesList(List<Car> cars);
    }
}
