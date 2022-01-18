package company.surious.treepoint.ui.common.fragments.identification

import androidx.databinding.InverseMethod
import company.surious.domain.entities.identification.HealthAssessmentMode

object HealthAssessmentBindingConverter {

    fun convertHealthIdentificationModeToInt(mode: HealthAssessmentMode): Int = mode.ordinal

    @InverseMethod("convertHealthIdentificationModeToInt")
    fun convertOrdinalToHealthIdentificationMode(ordinal: Int): HealthAssessmentMode =
        HealthAssessmentMode.values()[ordinal]
}