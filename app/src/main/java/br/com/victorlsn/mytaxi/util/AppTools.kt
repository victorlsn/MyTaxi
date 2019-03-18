package br.com.victorlsn.mytaxi.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

/**
 * Created by victorlsn on 28/02/19.
 *
 */
object AppTools {

    fun isOnline(context: Context): Boolean {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun showToast(context: Context?, message: String?, duration: Int) {
        if (null == context) return
        if (null == message || message.isEmpty()) return
        if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) return

        Toast.makeText(context, message, duration).show()

    }
}
