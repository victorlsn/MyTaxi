package br.com.victorlsn.mytaxi.models

import android.content.Context

import br.com.victorlsn.mytaxi.data.Controller
import br.com.victorlsn.mytaxi.interfaces.CarListMVP
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by victorlsn on 26/02/19.
 *
 */


class CarListModelImp(private val presenter: CarListMVP.Presenter) : CarListMVP.Model {

    override fun getVehicles(context: Context, coordinates: Map<String, String>) {
        try {
            Controller.getInstance().doApiCall(context)!!.getVehiclesList(coordinates).enqueue(object : Callback<br.com.victorlsn.mytaxi.beans.Response> {
                override fun onResponse(call: Call<br.com.victorlsn.mytaxi.beans.Response>, response: Response<br.com.victorlsn.mytaxi.beans.Response>) {
                    if (response.code() == 200 && response.body() != null) {
                        val carList = response.body()!!.cars

                        if (carList != null) {
                            presenter.requestVehiclesSuccessfully(carList)
                        }
                    }
                }

                override fun onFailure(call: Call<br.com.victorlsn.mytaxi.beans.Response>, t: Throwable) {
                    presenter.requestVehiclesFailure(t.message!!)
                }
            })
        } catch (e: Exception) {
            presenter.requestVehiclesFailure(null)
        }

    }
}
