package com.functrco.sail.screens.main.products

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration(
    private val spaceCount: Int,
    private val rowSpace: Float,
    private val columnSpacing: Float
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) //  obtain view  stay adapter Position in .

        val column = position % spaceCount // view  Column
        outRect.left = (column * columnSpacing / spaceCount).toInt() // column * ( Column spacing  * (1f /  Number of columns ))

        outRect.right =
            (columnSpacing - (column + 1) * columnSpacing / spaceCount).toInt() //  Column spacing  - (column + 1) * ( Column spacing  * (1f / Number of columns ))

        //  If position >  Row number , The description is not on the first line , The row height is not specified , The top spacing of other lines is  top=rowSpace
        if (position >= spaceCount) {
            outRect.top = rowSpace.toInt() // item top
        }
    }
}