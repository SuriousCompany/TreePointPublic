package company.surious.domain.use_case.identification

import company.surious.domain.entities.identification.IdentificationUsageInfo
import company.surious.domain.repositories.IdentificationRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetUsageInfoUseCase(private val identificationRepository: IdentificationRepository) :
    SingleUseCase<Void?, IdentificationUsageInfo>() {

    override fun createSingle(params: Void?): Single<IdentificationUsageInfo> =
        identificationRepository.getUsageInfo()
}