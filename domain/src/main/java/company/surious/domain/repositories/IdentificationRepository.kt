package company.surious.domain.repositories

import company.surious.domain.entities.identification.IdentificationRequest
import company.surious.domain.entities.identification.IdentificationUsageInfo
import company.surious.domain.entities.identification.result.IdentificationResult
import io.reactivex.rxjava3.core.Single

interface IdentificationRepository {
    fun getUsageInfo(): Single<IdentificationUsageInfo>
    fun identify(identificationRequest: IdentificationRequest): Single<IdentificationResult>
}