package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.mappers.Mapper
import company.surious.data.mappers.TreeTypeMapper
import company.surious.data.models.FirestoreTreeType
import company.surious.domain.entities.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import io.reactivex.Observable
import io.reactivex.Single
import kotlin.reflect.KClass

class FirestoreTreeTypeRepository(firebaseFirestore: FirebaseFirestore) :
    SimpleCollectionRepository<FirestoreTreeType, TreeType>(firebaseFirestore), TreeTypeRepository {

    override val collectionName: String = FirestoreContract.Collections.TREE_TYPES
    override val firestoreModelClass: KClass<FirestoreTreeType> = FirestoreTreeType::class
    override val mapper: Mapper<FirestoreTreeType, TreeType> = TreeTypeMapper

    override fun getTreeType(id: String): Single<TreeType> = get(id)

    override fun observeTreeType(id: String): Observable<TreeType> = observe(id)

    override fun observeAllTreeTypes(): Observable<List<TreeType>> = observe()

    override fun getAllTreeTypes(): Single<List<TreeType>> = getAll()
}