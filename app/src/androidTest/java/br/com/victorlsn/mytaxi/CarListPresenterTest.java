package br.com.victorlsn.mytaxi;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import br.com.victorlsn.mytaxi.presenters.CarListPresenterImp;

/**
 * Created by Victor on 28/02/2019.
 */

@RunWith(MockitoJUnitRunner.class)
public class CarListPresenterTest {
    CarListPresenterImp carListPresenter;

    @Mock
    CarListMVP.Model carListModel;

    @Mock
    CarListMVP.View carListView;


    @Before
    public void setup() throws Exception{
        carListPresenter = new CarListPresenterImp();
        carListPresenter.attachView(carListView);
//        carListModel = new CarListModelImp(carListPresenter);
        carListPresenter.model = carListModel;
    }

    @Test
    public void testRequestVehicles(){
        Context context = InstrumentationRegistry.getTargetContext();
        carListPresenter.requestVehicles(context);

        Map<String, String> coordinates = new HashMap<>();
        coordinates.put("p1Lat", context.getString(R.string.p1_lat));
        coordinates.put("p1Lon", context.getString(R.string.p1_lon));
        coordinates.put("p2Lat", context.getString(R.string.p2_lat));
        coordinates.put("p2Lon", context.getString(R.string.p2_lon));

        Mockito.verify(carListModel).getVehicles(context, coordinates);
    }

    @Test
    public void testRequestVehiclesSuccessfully(){
        carListPresenter.requestVehiclesSuccessfully(new ArrayList<Car>());
        Mockito.verify(carListView).receiveVehiclesList(new ArrayList<Car>());
    }

    @Test
    public void testRequestVehiclesFailure(){
        carListPresenter.requestVehiclesFailure(null);
        Mockito.verify(carListView).receiveVehiclesList(null);
    }
}
