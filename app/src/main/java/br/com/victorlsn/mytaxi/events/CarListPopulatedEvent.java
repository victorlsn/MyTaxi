package br.com.victorlsn.mytaxi.events;

import java.util.List;

import br.com.victorlsn.mytaxi.beans.Car;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class CarListPopulatedEvent {
    public CarListPopulatedEvent(List<Car> carList) {
        this.carList = carList;
    }

    private List<Car> carList;

    public List<Car> getCarList() {
        return carList;
    }
}
