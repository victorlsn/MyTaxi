package br.com.victorlsn.mytaxi.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.beans.Car;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import br.com.victorlsn.mytaxi.presenters.CarListPresenterImp;
import br.com.victorlsn.mytaxi.ui.adapters.CarListAdapter;
import br.com.victorlsn.mytaxi.ui.widget.VerticalSpaceItemDecoration;
import br.com.victorlsn.mytaxi.util.AppTools;
import butterknife.BindView;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class CarListFragment extends BaseFragment implements CarListMVP.View {


    @BindView(R.id.empty_state_layout)
    LinearLayout emptyStateLinearLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout();
        initPresenter();
        presenter.requestVehicles(getContext());
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
        recyclerView.setHasFixedSize(true);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestVehicles(getContext());
            }
        });
    }

    private void configAdapter(List<Car> cars) {
        adapter = new CarListAdapter(getActivity(), cars);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgressBar(boolean show, String message) {
        if (progressDialog == null && show) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(getString(R.string.wait));
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message, int duration) {
        AppTools.showToast(getActivity(), message, duration);
    }

    @Override
    public void receiveVehiclesList(List<Car> cars) {
        swipeRefreshLayout.setRefreshing(false);
        if (recyclerView != null && cars != null) {
            emptyStateLinearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (recyclerView.getAdapter() == null) {
                initRecyclerView();
            }
            configAdapter(cars);
        } else if (recyclerView != null) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyStateLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
