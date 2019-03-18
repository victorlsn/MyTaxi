package br.com.victorlsn.mytaxi.presenters

import android.content.Context
import android.util.Log
import android.widget.Toast

import org.greenrobot.eventbus.EventBus

import java.util.HashMap

import br.com.victorlsn.mytaxi.R
import br.com.victorlsn.mytaxi.beans.Car
import br.com.victorlsn.mytaxi.events.CarListPopulatedEvent
import br.com.victorlsn.mytaxi.interfaces.BaseMVP
import br.com.victorlsn.mytaxi.interfaces.CarListMVP
import br.com.victorlsn.mytaxi.models.CarListModelImp

/**
 * Created by victorlsn on 26/02/19.
 *
 */

class CarListPresenterImp : CarListMVP.Presenter {
    private val model: CarListMVP.Model
    private var view: CarListMVP.View? = null

    init {
        this.model = CarListModelImp(this)
    }

    override fun attachView(view: BaseMVP.View) {
        if (view == null) return
        this.view = view as CarListMVP.View?
    }

    override fun requestVehicles(context: Context) {
        try {
            view!!.showProgressBar(true, context.getString(R.string.loading_dialog))

            val coordinates = HashMap<String, String>()
            coordinates.put("p1Lat", context.getString(R.string.p1_lat))
            coordinates.put("p1Lon", context.getString(R.string.p1_lon))
            coordinates.put("p2Lat", context.getString(R.string.p2_lat))
            coordinates.put("p2Lon", context.getString(R.string.p2_lon))
            model.getVehicles(context, coordinates)
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }

    }

    override fun requestVehiclesSuccessfully(cars: List<Car>) {
        view!!.showProgressBar(false, null)
        view!!.receiveVehiclesList(cars)
        EventBus.getDefault().post(CarListPopulatedEvent(cars))

        for (car in cars) {
            Log.d("Car: ", car.toString())
        }
    }

    override fun requestVehiclesFailure(error: String?) {
        if (error != null) {
            view!!.showToast(error, Toast.LENGTH_SHORT)
        }
        view!!.showProgressBar(false, null)
        view!!.receiveVehiclesList(null)
        EventBus.getDefault().post(CarListPopulatedEvent(null))
    }
}
