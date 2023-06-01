package co.app.food.tabs.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.app.food.andromeda.extensions.makeVisible
import co.app.food.landing.databinding.FragmentTabBinding
import co.app.food.tabs.domain.TabState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

private const val TAB_ARGS = "tab_args"

interface TabScreen {
    val event: LiveData<TabEvent>

    fun bind(
        binding: FragmentTabBinding,
        observable: Observable<TabState>
    ): Disposable
}

class TabScreenImpl : TabScreen {
    private val _event = MutableLiveData<TabEvent>()
    override val event: LiveData<TabEvent> = _event

    override fun bind(
        binding: FragmentTabBinding,
        observable: Observable<TabState>
    ): Disposable {
        return CompositeDisposable().apply {
            handleContent(binding, observable, this)
            handleLoading(binding, observable, this)
            handleError(binding, observable, this)
        }
    }

    private fun handleContent(
        binding: FragmentTabBinding,
        observable: Observable<TabState>,
        bag: CompositeDisposable
    ) {
    }

    private fun handleLoading(
        binding: FragmentTabBinding,
        observable: Observable<TabState>,
        bag: CompositeDisposable
    ) {
    }

    private fun handleError(
        binding: FragmentTabBinding,
        observable: Observable<TabState>,
        bag: CompositeDisposable
    ) {
        binding.errorView.makeVisible()
        binding.errorView.text = "Coming Soon !!"
    }
}
