package br.com.victorlsn.mytaxi.interfaces;

/**
 * Created by victorlsn on 26/02/19.
 */

public interface BaseMVP {

    interface Presenter {
        boolean attachView(BaseMVP.View view);
    }

    interface View {
        boolean showProgressBar(boolean show, String message);

        boolean showToast(String message, int duration);
    }
}