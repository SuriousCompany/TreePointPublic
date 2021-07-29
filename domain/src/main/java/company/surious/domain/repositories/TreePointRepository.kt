package company.surious.domain.repositories

import company.surious.domain.entities.TreePoint
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface TreePointRepository {
    fun updateTreePoint(treePoint: TreePoint): Completable
    fun getAllTreePoints(): Single<List<TreePoint>>
    fun observeAllTreePoints(): Observable<List<TreePoint>>
    fun getTreePoint(id: String): Single<TreePoint>
    fun observeTreePoint(id: String): Observable<TreePoint>
}