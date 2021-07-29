package company.surious.treepoint.ui.common.fragments.create_tree_point

import android.content.Context
import company.surious.treepoint.R

object FruitingRangeTextHelper {
    fun getFruitingRangeText(context: Context, fruitingRange: Pair<Int, Int>?): String {
        return if (fruitingRange != null) {
            val start = fruitingRange.first
            val end = fruitingRange.second
            val months = context.resources.getStringArray(R.array.months)
            if (start == end) {
                context.getString(R.string.fruition_single_month, months[start])
            } else {
                context.getString(R.string.fruition_months_range, months[start], months[end])
            }
        } else {
            context.getString(R.string.select_fruiting_season)
        }
    }

    fun getFruitingRangeText(
        context: Context,
        fruitingStartMonth: Int,
        fruitingEndMonth: Int
    ): String {
        val pair =
            if (fruitingStartMonth == -1 && fruitingEndMonth == -1) {
                null
            } else {
                Pair(fruitingStartMonth, fruitingEndMonth)
            }
        return getFruitingRangeText(context, pair)
    }
}