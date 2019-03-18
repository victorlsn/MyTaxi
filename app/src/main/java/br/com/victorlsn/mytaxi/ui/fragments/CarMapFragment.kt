package br.com.victorlsn.mytaxi.ui.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import br.com.victorlsn.mytaxi.R
import br.com.victorlsn.mytaxi.beans.Car
import br.com.victorlsn.mytaxi.beans.Coordinate
import br.com.victorlsn.mytaxi.events.CarListPopulatedEvent

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class CarMapFragment : BaseFragment() {

    private lateinit var map: GoogleMap
    private lateinit var carList: List<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)
    }

    override fun layoutToInflate(): Int {
        return R.layout.car_map_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.frg) as SupportMapFragment
        mapFragment.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            val builder = LatLngBounds.Builder()
            builder.include(LatLng(53.394655, 9.757589)).include(LatLng(53.694865, 10.099891))


            mMap.setLatLngBoundsForCameraTarget(builder.build())

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0))

            mMap.setMinZoomPreference(10.0f)
            mMap.setMaxZoomPreference(20.0f)

            mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

                override fun getInfoWindow(arg0: Marker): View? {
                    return null
                }

                override fun getInfoContents(marker: Marker): View {

                    val info = LinearLayout(context)
                    info.orientation = LinearLayout.VERTICAL

                    val title = TextView(context)
                    title.setTextColor(Color.BLACK)
                    title.gravity = Gravity.CENTER
                    title.setTypeface(null, Typeface.BOLD)
                    title.text = marker.title

                    val snippet = TextView(context)
                    snippet.setTextColor(Color.GRAY)
                    snippet.text = marker.snippet

                    info.addView(title)
                    info.addView(snippet)

                    return info
                }
            })

            map = mMap
        }
    }

    @Subscribe
    fun onEvent(event: CarListPopulatedEvent) {
        map.clear()
        if (event.carList != null) {
            this.carList = event.carList
            populateMap(carList)
        }
    }

    private fun populateMap(cars: List<Car>) {
        for (car in cars) {
            val carMarker = MarkerOptions()
                    .position(LatLng(car.coordinate!!.latitude, car.coordinate.longitude))
                    .title(car.id.toString())
                    .snippet(String.format("Lat: %s%nLon: %s", car.coordinate.latitude, car.coordinate!!.longitude))
                    .icon(bitmapDescriptorFromVector(activity,
                            if (car.fleetType == "TAXI") R.drawable.ic_taxi_marker else R.drawable.ic_car_marker))
                    .rotation(car.heading.toFloat())
                    .flat(true)


            map.addMarker(carMarker)
        }
    }

    fun zoomInCoordinates(coordinate: Coordinate) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(coordinate.latitude, coordinate.longitude), 15.0f))
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context!!, vectorResId)!!
        val scaledWidth = vectorDrawable.intrinsicWidth / 2
        val scaledHeight = vectorDrawable.intrinsicHeight / 2
        vectorDrawable.setBounds(0, 0, scaledWidth, scaledHeight)
        val bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}