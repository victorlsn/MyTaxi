package br.com.victorlsn.mytaxi.ui.adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.beans.Coordinate;
import br.com.victorlsn.mytaxi.events.CarSelectedEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by victorlsn on 27/02/19.
 */

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> cars;

    private Context context;

    /**
     * Here is the key method to apply the animation
     */
    private int lastPosition = -1;

    public CarListAdapter(Context context, List<Car> cars) {
        this.cars = cars;

        this.context = context;
    }

    @NonNull
    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cars, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.setIsRecyclable(true);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Car car = cars.get(position);

        holder.id.setText(String.format("Car ID: %s", String.valueOf(car.getId())));
        holder.address.setText(R.string.loading_address);

        if (car.getFleetType().equals("TAXI")) {
            holder.icon.setImageResource(R.drawable.ic_taxi);
        }
        else {
            holder.icon.setImageResource(R.drawable.ic_car);
        }

        setAnimation(holder.parentLayout, position);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new CarSelectedEvent(car));
            }
        });

        if (car.getEstimatedAddress() == null) {
            new ReverseGeocodingTask(context, holder, car).execute(car.getCoordinate());
        }
        else {
            holder.address.setText(car.getEstimatedAddress());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return cars.get(position).getId();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.car_id_tv)
        TextView id;
        @BindView(R.id.car_address_tv)
        TextView address;
        @BindView(R.id.car_icon_iv)
        ImageView icon;
        @BindView(R.id.layout_parent)
        ConstraintLayout parentLayout;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    static class ReverseGeocodingTask extends AsyncTask<Coordinate, Void, String> {
        Context context;
        ViewHolder viewHolder;
        Car car;

        ReverseGeocodingTask(Context context, ViewHolder viewHolder, Car car) {
            super();
            this.context = context;
            this.viewHolder = viewHolder;
            this.car = car;
        }

        @Override
        protected String doInBackground(Coordinate... params) {
            Coordinate coordinate = params[0];
            try {
                Geocoder geoCoder = new Geocoder(context);
                List<Address> matches = geoCoder.getFromLocation(coordinate.getLatitude(), coordinate.getLongitude(), 1);
                Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                if (bestMatch != null) {
                    return String.format("%s:%n%s%s%s%s", "Approximated address",
                            (bestMatch.getThoroughfare() != null ? bestMatch.getThoroughfare()+" " : ""),
                            bestMatch.getSubThoroughfare() != null ? bestMatch.getSubThoroughfare() + ", " : (bestMatch.getThoroughfare() != null ? ", " : ""),
                            bestMatch.getPostalCode() != null ? bestMatch.getPostalCode()+" " : "",
                            bestMatch.getAdminArea() != null ? bestMatch.getAdminArea() : "");
                }
            }
            catch (Exception e) {
                Log.e("Error getting address", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String address) {
            super.onPostExecute(address);
            car.setEstimatedAddress(address);
            viewHolder.address.setText(address);
        }
    }

}
