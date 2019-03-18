package br.com.victorlsn.mytaxi.ui.activities

import android.annotation.SuppressLint
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import butterknife.ButterKnife

/**
 * Created by victorlsn on 27/02/19.
 *
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.setDebug(true)
        ButterKnife.bind(this)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
