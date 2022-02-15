package company.surious.treepoint.ui.common.view_models.identification

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import company.surious.domain.entities.identification.HealthAssessmentMode
import company.surious.domain.entities.identification.IdentificationAccuracy
import company.surious.domain.entities.identification.IdentificationRequest
import company.surious.domain.entities.identification.result.IdentificationResult
import company.surious.domain.entities.identification.result.IdentificationSuggestion
import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.entities.users.CreditsAddedEvent
import company.surious.domain.extensions.safeOnError
import company.surious.domain.extensions.safeOnSuccess
import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.identification.IdentifyUseCase
import company.surious.domain.use_case.tree_point.SavePlantDetailsUseCase
import company.surious.treepoint.ui.common.glide.ImageUtils
import company.surious.treepoint.ui.common.view_models.base.LoadingViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class IdentificationViewModel(
    private val identifyUseCase: IdentifyUseCase,
    private val savePlantDetailsUseCase: SavePlantDetailsUseCase
) : LoadingViewModel() {
    override var isLoadingByDefault: Boolean = false

    private val identificationSource = MutableLiveData<IdentificationResult>()
    private val addedCreditsSource = MutableLiveData<CreditsAddedEvent>()

    val addedCreditsEvent = addedCreditsSource as LiveData<CreditsAddedEvent>
    val identificationResult = identificationSource as LiveData<IdentificationResult>

    fun identify(
        contentResolver: ContentResolver,
        photos: List<Uri>,
        healthAssessmentMode: HealthAssessmentMode,
        lat: Float? = null,
        lng: Float? = null
    ) {
        isLoading.value = true
        disposables.add(
            encodeImages(contentResolver, photos).subscribe(
                { images ->
                    val request =
                        IdentificationRequest(
                            images,
                            healthAssessmentMode,
                            IdentificationAccuracy.HIGH,
                            lat,
                            lng
                        )
                    disposables.add(
                        identifyUseCase.execute(request).subscribe(
                            { identificationResult ->
                                identificationSource.value = identificationResult
                                savePlantDetails(
                                    identificationResult.suggestions.map(
                                        IdentificationSuggestion::plantDetails
                                    )
                                )
                            },
                            {
                                logUnhandledError(it, "identification error")
                            })
                    )
                },
                {
                    logUnhandledError(it, "images encoding error")
                })
        )

    }

    private fun savePlantDetails(details: List<PlantDetails>) {
        disposables.add(savePlantDetailsUseCase.execute(details).subscribe(
            { creditsAddedEvent ->
                addedCreditsSource.value = creditsAddedEvent
                isLoading.value = false
            },
            {
                logUnhandledError(it, "saving plant details error")
            },
            {
                isLoading.value = false
            }
        ))
    }

    //TODO move it to the data layer
    private fun encodeImages(
        contentResolver: ContentResolver,
        photos: List<Uri>
    ): Single<List<String>> =
        Single.create<List<String>> { emitter ->
            try {
                val encodedPhotos = photos.map { ImageUtils.encodeImage(contentResolver, it) }
                emitter.safeOnSuccess(encodedPhotos)
            } catch (error: Throwable) {
                emitter.safeOnError(error)
            }
        }.subscribeOn(Schedulers.computation())
}