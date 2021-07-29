package company.surious.domain.use_case.base

import androidx.lifecycle.LiveData

interface LiveDataUseCase<Params, Result> {

    fun execute(params: Params): LiveData<Result>
}