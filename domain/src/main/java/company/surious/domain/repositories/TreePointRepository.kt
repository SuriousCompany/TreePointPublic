package company.surious.domain.repositories

import company.surious.domain.entities.plants.TreePoint
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TreePointRepository {
    fun createTreePoint(treePoint: TreePoint): Single<String>
    fun updateTreePoint(treePoint: TreePoint): Completable
    fun getAllTreePoints(): Single<List<TreePoint>>
    fun observeAllTreePoints(): Observable<List<TreePoint>>
    fun getTreePoint(id: String): Single<TreePoint>
    fun observeTreePoint(id: String): Observable<TreePoint>
}