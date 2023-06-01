package co.app.food.tabs.domain

import androidx.lifecycle.MutableLiveData
import co.app.food.common.addTo
import co.app.food.common.base.BaseViewModel
import co.app.food.common.singleIo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TabViewModel @Inject constructor(
    private val useCase: TabUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<TabState>()
    val state = _state

    fun loadTabContent(url: String) {
        _state.value = TabState.ShowLoading
        useCase.loadTabContent(url)
            .compose(singleIo())
            .subscribe(
                { data ->
                    if (data.isNotEmpty()) {
                        _state.value = TabState.TabContentLoaded(data)
                    } else {
                        _state.value = TabState.TabContentEmpty("Empty")
                    }
                    _state.value = TabState.HideLoading
                },
                {
                    useCase.trackError(it)
                    _state.value = TabState.TabContentLoadFailed(it.message ?: "")
                    _state.value = TabState.HideLoading
                }
            )
            .addTo(bag)
    }

    fun trackError(error: Throwable) {
        useCase.trackError(error)
    }
}
