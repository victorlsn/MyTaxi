package br.com.victorlsn.mytaxi.ui.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.interfaces.CarListMVP;
import br.com.victorlsn.mytaxi.ui.adapters.PageFragmentAdapter;
import br.com.victorlsn.mytaxi.ui.fragments.CarListFragment;
import br.com.victorlsn.mytaxi.ui.fragments.CarMapFragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    CarListMVP.Presenter presenter;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_viewpager)
    Toolbar toolbar;


    private android.support.v7.app.ActionBar actionBar;

    private CarListFragment carListFragment;
    private CarMapFragment carMapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareActionBar();
        setupFragments();
        setupViewPager();
        setupTabLayout();
        setupTabClick();
//        actionBar.setTitle("THE IDDOG");
    }

    private void prepareActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
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

        adapter.addFragment(carListFragment, "Cars List");
        adapter.addFragment(carMapFragment, "Cars Map");
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
}
