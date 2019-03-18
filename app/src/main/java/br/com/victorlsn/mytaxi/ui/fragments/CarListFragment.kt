package br.com.victorlsn.mytaxi.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout

import br.com.victorlsn.mytaxi.R
import br.com.victorlsn.mytaxi.beans.Car
import br.com.victorlsn.mytaxi.interfaces.CarListMVP
import br.com.victorlsn.mytaxi.presenters.CarListPresenterImp
import br.com.victorlsn.mytaxi.ui.adapters.CarListAdapter
import br.com.victorlsn.mytaxi.ui.widget.VerticalSpaceItemDecoration
import br.com.victorlsn.mytaxi.util.AppTools
import butterknife.BindView

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class CarListFragment : BaseFragment(), CarListMVP.View {


    @BindView(R.id.empty_state_layout)
    lateinit internal var emptyStateLinearLayout: LinearLayout
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipe_refresh_layout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var adapter: CarListAdapter? = null
    private var presenter: CarListMVP.Presenter? = null
    private var progressDialog: ProgressDialog? = null


    override fun layoutToInflate(): Int {
        return R.layout.car_list_fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSwipeRefreshLayout()
        initPresenter()
        presenter!!.requestVehicles(context!!)
    }

    private fun initPresenter() {
        if (null == presenter) {
            presenter = CarListPresenterImp()
            presenter!!.attachView(this)
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(VerticalSpaceItemDecoration(24))
        recyclerView!!.setHasFixedSize(true)
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout!!.setOnRefreshListener { presenter!!.requestVehicles(context!!) }
    }

    private fun configAdapter(cars: List<Car>) {
        adapter = CarListAdapter(activity!!, cars)
        recyclerView!!.adapter = adapter
    }

    override fun showProgressBar(show: Boolean, message: String?) {
        if (progressDialog == null && show) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setTitle(getString(R.string.wait))
            progressDialog!!.setMessage(message)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } else if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    override fun showToast(message: String, duration: Int) {
        AppTools.showToast(activity, message, duration)
    }

    override fun receiveVehiclesList(cars: List<Car>?) {
        swipeRefreshLayout!!.isRefreshing = false
        if (recyclerView != null && cars != null) {
            emptyStateLinearLayout!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
            if (recyclerView!!.adapter == null) {
                initRecyclerView()
            }
            configAdapter(cars)
        } else if (recyclerView != null) {
            recyclerView!!.visibility = View.INVISIBLE
            emptyStateLinearLayout!!.visibility = View.VISIBLE
        }
    }
}
