package br.com.victorlsn.mytaxi.ui.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by victorlsn on 27/02/19.
 *
 */

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpaceHeight
        }
        outRect.bottom = verticalSpaceHeight
    }
}