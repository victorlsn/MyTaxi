package br.com.victorlsn.mytaxi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by victorlsn on 28/02/19.
 */
public class AppTools {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showToast(Context context, String message, int duration) {
        if (null == context) return;
        if (null == message || message.isEmpty()) return;
        if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) return;

        Toast.makeText(context, message, duration).show();

    }
}
