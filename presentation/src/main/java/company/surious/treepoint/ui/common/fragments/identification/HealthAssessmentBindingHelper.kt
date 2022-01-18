package company.surious.treepoint.ui.common.fragments.identification

import android.content.Context
import androidx.core.content.ContextCompat
import company.surious.domain.entities.identification.HealthAssessmentMode
import company.surious.domain.entities.identification.HealthAssessmentMode.*
import company.surious.treepoint.R

object HealthAssessmentBindingHelper {

    fun mapText(context: Context, mode: HealthAssessmentMode) =
        context.getString(
            when (mode) {
                NEVER -> R.string.never
                AUTO -> R.string.auto
                ALWAYS -> R.string.always
            }
        )

    fun mapColor(context: Context, mode: HealthAssessmentMode) =
        ContextCompat.getColor(
            context,
            when (mode) {
                NEVER -> R.color.red
                AUTO -> R.color.yellowAccent
                ALWAYS -> R.color.green
            }
        )

    fun mapSeekBarBackground(context: Context, mode: HealthAssessmentMode) =
        ContextCompat.getDrawable(
            context,
            when (mode) {
                NEVER -> R.drawable.seekbar_red
                AUTO -> R.drawable.seekbar_yellow
                ALWAYS -> R.drawable.seekbar_green
            }
        )

    fun mapSeekBarThumb(context: Context, mode: HealthAssessmentMode) =
        ContextCompat.getDrawable(
            context,
            when (mode) {
                NEVER -> R.drawable.ic_circle_red
                AUTO -> R.drawable.ic_circle_yellow
                ALWAYS -> R.drawable.ic_circle_green
            }
        )
}