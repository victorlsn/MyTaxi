package br.com.victorlsn.mytaxi.ui.activities;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.victorlsn.mytaxi.R;
import br.com.victorlsn.mytaxi.util.AppTools;
import butterknife.ButterKnife;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        AppTools.showToast(this, getString(R.string.click_back_again), Toast.LENGTH_SHORT);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
