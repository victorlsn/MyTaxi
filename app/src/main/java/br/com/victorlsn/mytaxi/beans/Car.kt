package br.com.victorlsn.mytaxi.beans

import com.google.gson.annotations.Expose

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class Car {
    @Expose
    var id: Int = 0
    @Expose
    val coordinate: Coordinate? = null
    @Expose
    val fleetType: String? = null
    @Expose
    val heading: Double = 0.toDouble()
    var estimatedAddress: String? = null

    override fun toString(): String {
        return "Car{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                ", fleetType='" + fleetType + '\'' +
                ", heading=" + heading +
                '}'
    }
}
