package br.com.victorlsn.mytaxi.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.events.CarSelectedEvent;
import br.com.victorlsn.mytaxi.ui.adapters.PageFragmentAdapter;
import br.com.victorlsn.mytaxi.ui.fragments.CarListFragment;
import br.com.victorlsn.mytaxi.ui.fragments.CarMapFragment;
import butterknife.BindView;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private CarListFragment carListFragment;
    private CarMapFragment carMapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragments();
        setupViewPager();
        setupTabLayout();
        setupTabClick();

        EventBus.getDefault().register(this);
    }

    private void setupFragments() {
        if (carListFragment == null) carListFragment = new CarListFragment();
        if (carMapFragment == null) carMapFragment = new CarMapFragment();
    }

    /**
     * Método responsável por configurar o view pager com as fragments.
     */
    private void setupViewPager() {
        PageFragmentAdapter adapter = new PageFragmentAdapter(getSupportFragmentManager());

        adapter.addFragment(carListFragment);
        adapter.addFragment(carMapFragment);
        viewPager.setAdapter(adapter);
    }

    /**
     * Método responsável por configurar os icones na tab do view pager.
     */
    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.md_ic_car));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.md_ic_map));
    }

    @Subscribe
    public void onEvent(CarSelectedEvent event) {
        if (event.getCar() != null) {
            invalidateOptionsMenu();
            viewPager.setCurrentItem(1);
            carMapFragment.zoomInCoordinates(event.getCar().getCoordinate());
        }
    }

    private void setupTabClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                invalidateOptionsMenu();
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            viewPager.setCurrentItem(0);
        }
        else {
            super.onBackPressed();
        }
    }
}
