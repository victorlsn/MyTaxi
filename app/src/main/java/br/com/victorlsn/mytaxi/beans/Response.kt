package br.com.victorlsn.mytaxi.beans

import com.google.gson.annotations.SerializedName

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class Response {
    @SerializedName("poiList")
    var cars: List<Car>? = null
        internal set
}
