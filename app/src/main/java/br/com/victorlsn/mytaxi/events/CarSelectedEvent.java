package br.com.victorlsn.mytaxi.events;

import br.com.victorlsn.mytaxi.beans.Car;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class CarSelectedEvent {
    private Car car;

    public CarSelectedEvent(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}
