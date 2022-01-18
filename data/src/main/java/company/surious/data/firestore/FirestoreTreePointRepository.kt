package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.firestore.mappers.FirestoreTreePointMapper
import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.FirestoreTreePoint
import company.surious.domain.entities.plants.TreePoint
import company.surious.domain.repositories.TreePointRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class FirestoreTreePointRepository(firebaseFirestore: FirebaseFirestore) :
    SimpleCollectionRepository<FirestoreTreePoint, TreePoint>(firebaseFirestore),
    TreePointRepository {

    override val collectionName: String = FirestoreContract.Collections.TREE_POINTS
    override val firestoreModelClass = FirestoreTreePoint::class
    override val mapper: Mapper<FirestoreTreePoint, TreePoint> = FirestoreTreePointMapper

    override fun updateTreePoint(treePoint: TreePoint): Completable = update(treePoint)

    override fun createTreePoint(treePoint: TreePoint): Single<String> = createWithNewId(treePoint)

    override fun getAllTreePoints(): Single<List<TreePoint>> = getAll()

    override fun observeAllTreePoints(): Observable<List<TreePoint>> = observe()

    override fun getTreePoint(id: String): Single<TreePoint> = get(id)

    override fun observeTreePoint(id: String): Observable<TreePoint> = observe(id)
}