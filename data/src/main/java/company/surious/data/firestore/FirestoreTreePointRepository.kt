package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.mappers.Mapper
import company.surious.data.mappers.TreePointMapper
import company.surious.data.models.FirestoreTreePoint
import company.surious.domain.entities.TreePoint
import company.surious.domain.repositories.TreePointRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class FirestoreTreePointRepository(firebaseFirestore: FirebaseFirestore) :
    SimpleCollectionRepository<FirestoreTreePoint, TreePoint>(firebaseFirestore),
    TreePointRepository {

    override val collectionName: String = FirestoreContract.Collections.TREE_POINTS
    override val firestoreModelClass = FirestoreTreePoint::class
    override val mapper: Mapper<FirestoreTreePoint, TreePoint> = TreePointMapper

    override fun updateTreePoint(treePoint: TreePoint): Completable = update(treePoint)

    override fun createTreePoint(treePoint: TreePoint): Single<String> = create(treePoint)

    override fun getAllTreePoints(): Single<List<TreePoint>> = getAll()

    override fun observeAllTreePoints(): Observable<List<TreePoint>> = observe()

    override fun getTreePoint(id: String): Single<TreePoint> = get(id)

    override fun observeTreePoint(id: String): Observable<TreePoint> = observe(id)
}