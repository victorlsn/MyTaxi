package br.com.victorlsn.mytaxi.beans;

import com.google.gson.annotations.Expose;

/**
 * Created by victorlsn on 27/02/19.
 */

public class Car {
    @Expose
    private int id;
    @Expose
    private Coordinate coordinate;
    @Expose
    private String fleetType;
    @Expose
    private double heading;
    private String estimatedAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getFleetType() {
        return fleetType;
    }

    public double getHeading() {
        return heading;
    }

    public String getEstimatedAddress() {
        return estimatedAddress;
    }

    public void setEstimatedAddress(String estimatedAddress) {
        this.estimatedAddress = estimatedAddress;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                ", fleetType='" + fleetType + '\'' +
                ", heading=" + heading +
                '}';
    }
}
