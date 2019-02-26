package br.com.victorlsn.mytaxi.interfaces;

import java.util.Map;

/**
 * Created by victorlsn on 26/02/19.
 */

public interface CarListMVP {
    interface Model {
        void getVehicles(Map<String, String> coordinates);
    }

    interface Presenter extends BaseMVP.Presenter {
        void requestVehicles();

        void requestVehiclesSuccessfully();

        void requestVehiclesFailure();
    }

    interface View extends BaseMVP.View {
        void receiveVehiclesList();
    }
}
