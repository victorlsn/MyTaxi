package br.com.victorlsn.mytaxi.interfaces;

/**
 * Created by victorlsn on 26/02/19.
 */

public interface BaseMVP {

    interface Presenter {
        void attachView(View view);
    }

    interface View {
        void showProgressBar(boolean show, String message);

        void showToast(String message, int duration);
    }
}