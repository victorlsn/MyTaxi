package br.com.victorlsn.mytaxi.beans;

/**
 * Created by victorlsn on 27/02/19.
 */

public class Coordinate {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
