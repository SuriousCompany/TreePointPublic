package company.surious.domain.repositories

import company.surious.domain.entities.TreeType
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface TreeTypeRepository {
    fun getTreeType(id: String): Single<TreeType>
    fun observeTreeType(id: String): Observable<TreeType>
    fun observeAllTreeTypes(): Observable<List<TreeType>>
    fun getAllTreeTypes(): Single<List<TreeType>>

    fun add(type: TreeType): Completable
}