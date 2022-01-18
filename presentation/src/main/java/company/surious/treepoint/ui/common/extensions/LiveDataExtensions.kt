package company.surious.treepoint.ui.common.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun LiveData<Boolean>.and(anotherSource: LiveData<Boolean>): LiveData<Boolean> {
    val mediator = MediatorLiveData<Boolean>().apply { value = false }
    mediator.addSource(this) { isLoading ->
        mediator.value = isLoading || anotherSource.value!!
    }
    mediator.addSource(anotherSource) { isLoading ->
        mediator.value = isLoading || this.value!!
    }
    return mediator
}