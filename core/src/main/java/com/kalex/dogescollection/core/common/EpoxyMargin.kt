package com.kalex.dogescollection.core.common

import android.content.Context
import android.util.TypedValue

data class EpoxyMarginCon(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
) {
    companion object {
        fun dpToPx(dp: Float, context: Context): Int =
            context.dpToPx(dp)

        fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }
}
