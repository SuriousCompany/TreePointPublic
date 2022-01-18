package company.surious.domain.repositories

import io.reactivex.rxjava3.core.Completable


interface UserNameRepository {
    fun checkUserName(userName: String): Completable
    fun reserveUserName(userName: String): Completable
}