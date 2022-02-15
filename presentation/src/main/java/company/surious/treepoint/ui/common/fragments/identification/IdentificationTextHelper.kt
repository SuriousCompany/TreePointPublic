package company.surious.treepoint.ui.common.fragments.identification

object IdentificationTextHelper {
    fun getPercentsText(probability: Float) = "${(100 * probability).toInt()}%"
}