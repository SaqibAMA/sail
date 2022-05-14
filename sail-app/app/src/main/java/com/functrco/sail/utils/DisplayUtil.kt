package com.functrco.sail.utils

import android.content.res.Resources
import android.util.TypedValue

object DisplayUtil {

    // convert dp to dx
    fun dp2dx(dp: Float, resources: Resources): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
    }
}