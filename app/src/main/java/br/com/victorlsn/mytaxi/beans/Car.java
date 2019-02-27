package br.com.victorlsn.mytaxi.beans;

import com.google.gson.annotations.Expose;

/**
 * Created by victorlsn on 27/02/19.
 */

public class Car {
    @Expose
    int id;
    @Expose
    Coordinate coordinate;
    @Expose
    String fleetType;
    @Expose
    double heading;
    String estimatedAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getFleetType() {
        return fleetType;
    }

    public void setFleetType(String fleetType) {
        this.fleetType = fleetType;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
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
