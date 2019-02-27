package br.com.victorlsn.mytaxi.ui.adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
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

    private Context ctx;

    private OnItemClickListener mOnItemClickListener;
    /**
     * Here is the key method to apply the animation
     */
    private int lastPosition = -1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CarListAdapter(Context context, List<Car> cars) {
        this.cars = cars;

        ctx = context;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cars, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
            new ReverseGeocodingTask(ctx, holder, car).execute(car.getCoordinate());
        }
        else {
            holder.address.setText(car.getEstimatedAddress());
        }
    }

    public Car getItem(int position) {
        return cars.get(position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cars.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Car obj, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.car_id_tv)
        TextView id;
        @BindView(R.id.car_address_tv)
        TextView address;
        @BindView(R.id.car_icon_iv)
        ImageView icon;
        @BindView(R.id.layout_parent)
        ConstraintLayout parentLayout;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // AsyncTask encapsulating the reverse-geocoding API.  Since the geocoder API is blocked,
// we do not want to invoke it from the UI thread.
    static class ReverseGeocodingTask extends AsyncTask<Coordinate, Void, String> {
        Context ctx;
        ViewHolder viewHolder;
        Car car;

        public ReverseGeocodingTask(Context context, ViewHolder viewHolder, Car car) {
            super();
            ctx = context;
            this.viewHolder = viewHolder;
            this.car = car;
        }

        @Override
        protected String doInBackground(Coordinate... params) {
            Coordinate coordinate = params[0];
            try {
                Geocoder geoCoder = new Geocoder(ctx);
                List<Address> matches = geoCoder.getFromLocation(coordinate.getLatitude(), coordinate.getLongitude(), 1);
                Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                if (bestMatch != null) {
                    return String.format("%s:\n%s%s%s%s", "Approximated address",
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
