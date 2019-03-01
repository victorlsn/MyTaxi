package br.com.victorlsn.mytaxi.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.beans.Coordinate;
import br.com.victorlsn.mytaxi.events.CarListPopulatedEvent;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class CarMapFragment extends BaseFragment {

    GoogleMap map;
    List<Car> carList;

    public CarMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    protected int layoutToInflate() {return R.layout.car_map_fragment;}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(53.394655, 9.757589)).include(new LatLng(53.694865, 10.099891));


                mMap.setLatLngBoundsForCameraTarget(builder.build());

                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));

                mMap.setMinZoomPreference(10.0f);
                mMap.setMaxZoomPreference(20.0f);

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        LinearLayout info = new LinearLayout(getContext());
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(getContext());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(getContext());
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });

                map = mMap;
            }
        });
    }

    @Subscribe
    public void onEvent(CarListPopulatedEvent event) {
        map.clear();
        if (event.getCarList() != null) {
            this.carList = event.getCarList();
            populateMap(carList);
        }
    }

    private void populateMap(List<Car> cars) {
        for (Car car : cars) {
            MarkerOptions carMarker = new MarkerOptions()
                    .position(new LatLng(car.getCoordinate().getLatitude(), car.getCoordinate().getLongitude()))
                    .title(String.valueOf(car.getId()))
                    .snippet(String.format("Lat: %s%nLon: %s", car.getCoordinate().getLatitude(), car.getCoordinate().getLongitude()))
                    .icon(bitmapDescriptorFromVector(getActivity(),
                            car.getFleetType().equals("TAXI") ? R.drawable.ic_taxi_marker : R.drawable.ic_car_marker))
                    .rotation((float) (car.getHeading()))
                    .flat(true);


            map.addMarker(carMarker);
        }
    }

    public void zoomInCoordinates(Coordinate coordinate) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(coordinate.getLatitude(), coordinate.getLongitude()), 15.0f));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        assert vectorDrawable != null;
        int scaledWidth = (vectorDrawable.getIntrinsicWidth()/2);
        int scaledHeight = (vectorDrawable.getIntrinsicHeight()/2);
        vectorDrawable.setBounds(0, 0, scaledWidth, scaledHeight);
        Bitmap bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}