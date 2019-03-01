package br.com.victorlsn.mytaxi.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class Response {
    @SerializedName("poiList")
    List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }
}
