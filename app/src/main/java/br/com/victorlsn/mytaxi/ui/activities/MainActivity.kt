package br.com.victorlsn.mytaxi.ui.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import br.com.victorlsn.mytaxi.R
import br.com.victorlsn.mytaxi.events.CarSelectedEvent
import br.com.victorlsn.mytaxi.ui.adapters.PageFragmentAdapter
import br.com.victorlsn.mytaxi.ui.fragments.CarListFragment
import br.com.victorlsn.mytaxi.ui.fragments.CarMapFragment
import butterknife.BindView

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class MainActivity : BaseActivity() {

    @BindView(R.id.tabs)
    lateinit var tabLayout: TabLayout
    @BindView(R.id.viewPager)
    lateinit var viewPager: ViewPager

    private var carListFragment: CarListFragment? = null
    private var carMapFragment: CarMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragments()
        setupViewPager()
        setupTabLayout()
        setupTabClick()

        EventBus.getDefault().register(this)
    }

    private fun setupFragments() {
        if (carListFragment == null) carListFragment = CarListFragment()
        if (carMapFragment == null) carMapFragment = CarMapFragment()
    }

    /**
     * Método responsável por configurar o view pager com as fragments.
     */
    private fun setupViewPager() {
        val adapter = PageFragmentAdapter(supportFragmentManager)

        adapter.addFragment(carListFragment!!)
        adapter.addFragment(carMapFragment!!)
        viewPager!!.adapter = adapter
    }

    /**
     * Método responsável por configurar os icones na tab do view pager.
     */
    private fun setupTabLayout() {
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.getTabAt(0)!!.icon = resources.getDrawable(R.drawable.md_ic_car)
        tabLayout!!.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.md_ic_map)
    }

    @Subscribe
    fun onEvent(event: CarSelectedEvent) {
        if (event.car != null) {
            invalidateOptionsMenu()
            viewPager!!.currentItem = 1
            carMapFragment!!.zoomInCoordinates(event.car.coordinate!!)
        }
    }

    private fun setupTabClick() {
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                invalidateOptionsMenu()
                val position = tab.position
                viewPager!!.currentItem = position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onBackPressed() {
        if (viewPager!!.currentItem == 1) {
            viewPager!!.currentItem = 0
        } else {
            super.onBackPressed()
        }
    }
}
