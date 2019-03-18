package br.com.victorlsn.mytaxi.beans

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class Coordinate {

    val latitude: Double = 0.toDouble()
    val longitude: Double = 0.toDouble()

    override fun toString(): String {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}'
    }
}
