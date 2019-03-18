package br.com.victorlsn.mytaxi.interfaces

/**
 * Created by victorlsn on 26/02/19.
 *
 */

interface BaseMVP {

    interface Presenter {
        fun attachView(view: View)
    }

    interface View {
        fun showProgressBar(show: Boolean, message: String?)

        fun showToast(message: String, duration: Int)
    }
}