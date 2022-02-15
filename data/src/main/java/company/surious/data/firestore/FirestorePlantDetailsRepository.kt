package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.extensions.getDocumentsIdsAsync
import company.surious.data.firestore.FirestoreContract.Collections.PLANT_DETAILS
import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.mappers.plant_details.FirestorePlantDetailsMapper
import company.surious.data.firestore.models.plant_details.FirestorePlantDetails
import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.repositories.PlantDetailsRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.reflect.KClass

class FirestorePlantDetailsRepository(firebaseFirestore: FirebaseFirestore) :
    SimpleCollectionRepository<FirestorePlantDetails, PlantDetails>(firebaseFirestore),
    PlantDetailsRepository {

    override val collectionName: String = PLANT_DETAILS
    override val firestoreModelClass: KClass<FirestorePlantDetails> = FirestorePlantDetails::class
    override val mapper: Mapper<FirestorePlantDetails, PlantDetails> = FirestorePlantDetailsMapper

    override fun savePlantDetails(details: List<PlantDetails>): Single<Int> =
        firebaseFirestore.collection(collectionName).getDocumentsIdsAsync()
            .map { allDetailIds ->
                details.filter { updatedDetailsId ->
                    !allDetailIds.contains(updatedDetailsId.id)
                }
            }
            .subscribeOn(Schedulers.io())
            .flatMap { newDetails -> addAll(newDetails).map { it.size } }

    override fun getAllPlantDetails(): Single<List<PlantDetails>> = getAll()

    override fun getPlantDetailsById(id: Int): Single<PlantDetails> = get(id.toString())

}