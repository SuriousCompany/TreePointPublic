package company.surious.data.retrofit

import company.surious.data.retrofit.mappers.IdentificationRequestMapper
import company.surious.data.retrofit.mappers.IdentificationResultMapper
import company.surious.data.retrofit.mappers.IdentificationUsageInfoMapper
import company.surious.domain.entities.identification.IdentificationRequest
import company.surious.domain.entities.identification.IdentificationUsageInfo
import company.surious.domain.entities.identification.result.IdentificationResult
import company.surious.domain.repositories.IdentificationRepository
import io.reactivex.rxjava3.core.Single

class RetrofitIdentificationRepository(private val api: IdentificationApi) :
    IdentificationRepository {

    override fun getUsageInfo(): Single<IdentificationUsageInfo> =
        api.getUsageInfo().map(IdentificationUsageInfoMapper::mapToEntity)

    override fun identify(identificationRequest: IdentificationRequest): Single<IdentificationResult> =
        api.identify(IdentificationRequestMapper.mapToRetrofitModel(identificationRequest))
            .map(IdentificationResultMapper::map)

}