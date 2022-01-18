package company.surious.data.retrofit.mappers

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.retrofit.model.response.RetrofitIdentificationUsageInfo
import company.surious.domain.entities.identification.IdentificationUsageInfo

object IdentificationUsageInfoMapper :
    Mapper<RetrofitIdentificationUsageInfo, IdentificationUsageInfo> {
    override fun mapToEntity(model: RetrofitIdentificationUsageInfo) =
        with(model) {
            IdentificationUsageInfo(
                isActive,
                dailyLimit,
                weeklyLimit,
                monthlyLimit,
                totalLimit,
                isClosed,
                usedDay,
                usedWeek,
                usedMonth,
                usedTotal,
                remainingDay,
                remainingWeek,
                remainingMonth,
                remainingTotal
            )
        }

    override fun mapToDataModel(entity: IdentificationUsageInfo) =
        with(entity) {
            RetrofitIdentificationUsageInfo(
                isActive,
                dailyLimit,
                weeklyLimit,
                monthlyLimit,
                totalLimit,
                isClosed,
                usedDay,
                usedWeek,
                usedMonth,
                usedTotal,
                remainingDay,
                remainingWeek,
                remainingMonth,
                remainingTotal
            )
        }
}