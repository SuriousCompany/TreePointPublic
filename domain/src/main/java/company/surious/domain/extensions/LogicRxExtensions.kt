package company.surious.domain.extensions

import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.errors.TreeError
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

fun Maybe<RegisteredUser>.userOrError(userId: String) = switchIfEmpty(
    Single.error(TreeError.CommunicationError.NoUserFoundError(userId))
)