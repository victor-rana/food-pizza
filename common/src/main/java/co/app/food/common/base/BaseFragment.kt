package co.app.food.common.base

import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseFragment<S : ViewState> : Fragment() {
    val _state = PublishSubject.create<S>()
    val state = _state.hide()
    val compositeBag = CompositeDisposable()

    override fun onDestroy() {
        _state.onComplete()
        compositeBag.clear()
        super.onDestroy()
    }
}
