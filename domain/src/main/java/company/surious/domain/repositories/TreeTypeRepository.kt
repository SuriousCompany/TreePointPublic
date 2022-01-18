package company.surious.domain.repositories

import company.surious.domain.entities.plants.TreeType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TreeTypeRepository {
    fun getTreeType(id: String): Single<TreeType>
    fun observeTreeType(id: String): Observable<TreeType>
    fun observeAllTreeTypes(): Observable<List<TreeType>>
    fun getAllTreeTypes(): Single<List<TreeType>>
}