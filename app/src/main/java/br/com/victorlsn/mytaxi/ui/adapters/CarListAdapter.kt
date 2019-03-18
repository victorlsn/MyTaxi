package br.com.victorlsn.mytaxi.ui.adapters

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

import org.greenrobot.eventbus.EventBus

import br.com.victorlsn.mytaxi.R
import br.com.victorlsn.mytaxi.beans.Car
import br.com.victorlsn.mytaxi.beans.Coordinate
import br.com.victorlsn.mytaxi.events.CarSelectedEvent
import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class CarListAdapter(private val context: Context, private val cars: List<Car>) : RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    /**
     * Here is the key method to apply the animation
     */
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cars, parent, false)
        val vh = ViewHolder(v)
        vh.setIsRecyclable(true)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = cars[position]

        holder.id!!.text = String.format("Car ID: %s", car.id.toString())
        holder.address!!.setText(R.string.loading_address)

        if (car.fleetType == "TAXI") {
            holder.icon!!.setImageResource(R.drawable.ic_taxi)
        } else {
            holder.icon!!.setImageResource(R.drawable.ic_car)
        }

        setAnimation(holder.parentLayout, position)

        holder.parentLayout!!.setOnClickListener { EventBus.getDefault().post(CarSelectedEvent(car)) }

        if (car.estimatedAddress == null) {
            ReverseGeocodingTask(context, holder, car).execute(car.coordinate)
        } else {
            holder.address!!.text = car.estimatedAddress
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return cars[position].id.toLong()
    }

    private fun setAnimation(viewToAnimate: View?, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
            viewToAnimate!!.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @BindView(R.id.car_id_tv)
        @JvmField var id: TextView? = null
        @BindView(R.id.car_address_tv)
        @JvmField var address: TextView? = null
        @BindView(R.id.car_icon_iv)
        @JvmField var icon: ImageView? = null
        @BindView(R.id.layout_parent)
        @JvmField var parentLayout: ConstraintLayout? = null

        init {
            ButterKnife.bind(this, v)
        }
    }

    internal class ReverseGeocodingTask(var context: Context, var viewHolder: ViewHolder, var car: Car) : AsyncTask<Coordinate, Void, String>() {

        override fun doInBackground(vararg params: Coordinate): String? {
            val coordinate = params[0]
            try {
                val geoCoder = Geocoder(context)
                val matches = geoCoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1)
                val bestMatch = if (matches.isEmpty()) null else matches[0]
                if (bestMatch != null) {
                    return String.format("%s:%n%s%s%s%s", "Approximated address",
                            if (bestMatch.thoroughfare != null) bestMatch.thoroughfare + " " else "",
                            if (bestMatch.subThoroughfare != null) bestMatch.subThoroughfare + ", " else if (bestMatch.thoroughfare != null) ", " else "",
                            if (bestMatch.postalCode != null) bestMatch.postalCode + " " else "",
                            if (bestMatch.adminArea != null) bestMatch.adminArea else "")
                }
            } catch (e: Exception) {
                Log.e("Error getting address", e.message)
            }

            return null
        }

        override fun onPostExecute(address: String?) {
            super.onPostExecute(address)
            if (address != null) {
                car.estimatedAddress = address
                viewHolder.address!!.text = address
            } else {
                viewHolder.address!!.setText(R.string.error_address)
            }
        }
    }

}
