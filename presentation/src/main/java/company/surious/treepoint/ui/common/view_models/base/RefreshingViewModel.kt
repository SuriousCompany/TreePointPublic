package company.surious.treepoint.ui.common.view_models.base

import androidx.lifecycle.MutableLiveData
import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.base.SingleUseCase

abstract class RefreshingViewModel<Request, Data> : LoadingViewModel() {
    protected abstract val refreshingUseCase: SingleUseCase<Request, Data>

    protected open val dataSource by lazy { createDataSource() }
    protected open val defaultData: Data? = null

    private fun createDataSource(): MutableLiveData<Data> {
        val source = MutableLiveData<Data>()
        defaultData?.let { source.value = it }
        return source
    }

    protected open fun refresh(argument: Request) {
        disposables.add(
            refreshingUseCase.execute(argument).subscribe(
                { freshData ->
                    dataSource.value = freshData
                    isLoading.value = false
                },
                ::logUnhandledError
            )
        )
    }

}