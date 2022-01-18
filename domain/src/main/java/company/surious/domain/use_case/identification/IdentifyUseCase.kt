package company.surious.domain.use_case.identification

import company.surious.domain.entities.identification.IdentificationRequest
import company.surious.domain.entities.identification.result.IdentificationResult
import company.surious.domain.repositories.IdentificationRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class IdentifyUseCase(private val identificationRepository: IdentificationRepository) :
    SingleUseCase<IdentificationRequest, IdentificationResult>() {

    override fun createSingle(params: IdentificationRequest): Single<IdentificationResult> =
        identificationRepository.identify(params)
}