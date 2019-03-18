package br.com.victorlsn.mytaxi.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife

/**
 * Created by victorlsn on 27/02/19.
 *
 */

abstract class BaseFragment : Fragment() {
    protected abstract fun layoutToInflate(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutToInflate(), container, false)
        ButterKnife.bind(this, view)
        return view
    }
}