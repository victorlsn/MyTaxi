package br.com.victorlsn.mytaxi.interfaces;

import android.content.Context;

import java.util.List;
import java.util.Map;

import br.com.victorlsn.mytaxi.beans.Car;

/**
 * Created by victorlsn on 26/02/19.
 *
 */

public interface CarListMVP {
    interface Model {
        void getVehicles(Context context, Map<String, String> coordinates);
    }

    interface Presenter extends BaseMVP.Presenter {
        void requestVehicles(Context context);

        void requestVehiclesSuccessfully(List<Car> cars);

        void requestVehiclesFailure(String error);
    }

    interface View extends BaseMVP.View {
        void receiveVehiclesList(List<Car> cars);
    }
}
