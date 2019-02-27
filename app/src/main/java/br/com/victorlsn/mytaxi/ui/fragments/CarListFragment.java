package br.com.victorlsn.mytaxi.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import br.com.victorlsn.mytaxi.presenters.CarListPresenterImp;
import br.com.victorlsn.mytaxi.ui.adapters.CarListAdapter;
import br.com.victorlsn.mytaxi.ui.widget.VerticalSpaceItemDecoration;
import butterknife.BindView;

/**
 * Created by victorlsn on 27/02/19.
 */

public class CarListFragment extends BaseFragment implements CarListMVP.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private CarListAdapter adapter;
    private CarListMVP.Presenter presenter;
    private ProgressDialog progressDialog;


    @Override
    protected int layoutToInflate() {
        return R.layout.car_list_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        initPresenter();
        presenter.requestVehicles();
    }

    private void initPresenter() {
        if (null == presenter) {
            presenter = new CarListPresenterImp();
            presenter.attachView(this);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(24));
        recyclerView.setHasFixedSize(false);
    }

    private void configAdapter(List<Car> cars) {
        adapter = new CarListAdapter(getActivity(), cars);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Car obj, int position) {

            }
        });
    }

    @Override
    public boolean showProgressBar(boolean show, String message) {
        if (show) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Retrieving information...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            if (null != progressDialog && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        return true;
    }

    @Override
    public boolean showToast(String message, int duration) {
//        AppTools.showToast(getActivity(), message, duration);
        return true;
    }

    @Override
    public void receiveVehiclesList(List<Car> cars) {
        if (recyclerView != null && cars != null) {
            configAdapter(cars);
        }
    }
}
