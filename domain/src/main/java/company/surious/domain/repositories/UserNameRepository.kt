package company.surious.domain.repositories

import io.reactivex.Completable

interface UserNameRepository {
    fun checkUserName(userName: String): Completable
    fun reserveUserName(userName: String): Completable
}