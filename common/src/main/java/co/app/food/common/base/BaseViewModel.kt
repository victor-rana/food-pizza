package co.app.food.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val bag by lazy { CompositeDisposable() }

    override fun onCleared() {
        bag.clear()
        super.onCleared()
    }
}
