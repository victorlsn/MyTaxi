package br.com.victorlsn.mytaxi.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by victorlsn on 27/02/19.
 *
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}