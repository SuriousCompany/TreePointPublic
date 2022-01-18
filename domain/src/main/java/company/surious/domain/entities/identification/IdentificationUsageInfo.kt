package company.surious.domain.entities.identification

data class IdentificationUsageInfo(
    val isActive: Boolean = false,
    val dailyLimit: Int? = null,
    val weeklyLimit: Int? = null,
    val monthlyLimit: Int? = null,
    val totalLimit: Int? = null,
    val isClosed: Boolean = false,
    val usedDay: Int = 0,
    val usedWeek: Int = 0,
    val usedMonth: Int = 0,
    val usedTotal: Int = 0,
    val remainingDay: Int? = null,
    val remainingWeek: Int? = null,
    val remainingMonth: Int? = null,
    val remainingTotal: Int? = null
)