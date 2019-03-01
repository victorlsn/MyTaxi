package br.com.victorlsn.mytaxi.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public abstract class BaseFragment extends Fragment {
    protected abstract int layoutToInflate();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutToInflate(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}