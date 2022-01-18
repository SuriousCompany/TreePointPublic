package company.surious.treepoint.ui.common.view_models.identification

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import company.surious.domain.entities.identification.HealthAssessmentMode
import company.surious.domain.entities.identification.IdentificationAccuracy
import company.surious.domain.entities.identification.IdentificationRequest
import company.surious.domain.entities.identification.result.IdentificationResult
import company.surious.domain.extensions.safeOnError
import company.surious.domain.extensions.safeOnSuccess
import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.identification.IdentifyUseCase
import company.surious.treepoint.ui.common.view_models.base.LoadingViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream

class IdentificationViewModel(private val identifyUseCase: IdentifyUseCase) : LoadingViewModel() {
    override var isLoadingByDefault: Boolean = false

    private val identificationSource = MutableLiveData<IdentificationResult>()

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
                                isLoading.value = false
                                identificationSource.value = identificationResult

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

    //TODO move it to data layer
    private fun encodeImages(
        contentResolver: ContentResolver,
        photos: List<Uri>
    ): Single<List<String>> =
        Single.create<List<String>> { emitter ->
            try {
                val encodedPhotos = photos.map { encodeImage(contentResolver, it) }
                emitter.safeOnSuccess(encodedPhotos)
            } catch (error: Throwable) {
                emitter.safeOnError(error)
            }
        }.subscribeOn(Schedulers.computation())

    private fun encodeImage(contentResolver: ContentResolver, uri: Uri): String {
//TODO handle sdk version properly
        val bitmap = ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                contentResolver,
                uri
            )
        )
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val bytes: ByteArray = outputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}