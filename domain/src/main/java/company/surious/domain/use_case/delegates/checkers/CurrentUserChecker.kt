package company.surious.domain.use_case.delegates.checkers

import company.surious.domain.errors.TreeError.PreferencesError
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

object CurrentUserChecker {
    private val error by lazy { PreferencesError(customMessage = "User id is not initialized") }

    fun <T : Any> idOrErrorSingle(id: String?, action: (String) -> Single<T>) =
        if (id != null) {
            action.invoke(id)
        } else {
            Single.error(error)
        }

    fun <T : Any> idOrErrorMaybe(id: String?, action: (String) -> Maybe<T>) =
        if (id != null) {
            action.invoke(id)
        } else {
            Maybe.error(error)
        }
}