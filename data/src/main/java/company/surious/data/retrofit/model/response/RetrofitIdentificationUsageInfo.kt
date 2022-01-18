package company.surious.data.retrofit.model.response

import com.google.gson.annotations.SerializedName

data class RetrofitIdentificationUsageInfo(
    @SerializedName("active")
    var isActive: Boolean = false,
    @SerializedName("daily_limit")
    var dailyLimit: Int? = null,
    @SerializedName("weekly_limit")
    var weeklyLimit: Int? = null,
    @SerializedName("monthly_limit")
    var monthlyLimit: Int? = null,
    @SerializedName("total_limit")
    var totalLimit: Int? = null,
    @SerializedName("is_closed")
    var isClosed: Boolean = false,
    @SerializedName("used_day")
    var usedDay: Int = 0,
    @SerializedName("used_week")
    var usedWeek: Int = 0,
    @SerializedName("used_month")
    var usedMonth: Int = 0,
    @SerializedName("used_total")
    var usedTotal: Int = 0,
    @SerializedName("remaining_day")
    var remainingDay: Int? = null,
    @SerializedName("remaining_week")
    var remainingWeek: Int? = null,
    @SerializedName("remaining_month")
    var remainingMonth: Int? = null,
    @SerializedName("remaining_total")
    var remainingTotal: Int? = null
)