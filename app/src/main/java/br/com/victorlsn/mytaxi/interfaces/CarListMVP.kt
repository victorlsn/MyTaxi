package br.com.victorlsn.mytaxi.interfaces

import android.content.Context

import br.com.victorlsn.mytaxi.beans.Car

/**
 * Created by victorlsn on 26/02/19.
 *
 */

interface CarListMVP {
    interface Model {
        fun getVehicles(context: Context, coordinates: Map<String, String>)
    }

    interface Presenter : BaseMVP.Presenter {
        fun requestVehicles(context: Context)

        fun requestVehiclesSuccessfully(cars: List<Car>)

        fun requestVehiclesFailure(error: String?)
    }

    interface View : BaseMVP.View {
        fun receiveVehiclesList(cars: List<Car>?)
    }
}
