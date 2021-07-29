package company.surious.treepoint.ui.common.view_models.base

import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.base.ObservableUseCase
import io.reactivex.disposables.Disposable

abstract class ObservableViewModel<Request, Data> : RefreshingViewModel<Request, Data>() {
    protected abstract val observableUseCase: ObservableUseCase<Request, Data>
    protected open var observingDisposable: Disposable? = null

    protected open fun startObserving(argument: Request) {
        if (observingDisposable == null) {
            observingDisposable =
                observableUseCase.execute(argument).subscribe(
                    { updatedData ->
                        dataSource.value = updatedData
                    },
                    ::logUnhandledError
                )
            disposables.add(observingDisposable!!)
        }
    }

    fun stopObserving() {
        observingDisposable?.dispose()
        observingDisposable = null
    }

    override fun onCleared() {
        super.onCleared()
        observingDisposable = null
    }
}