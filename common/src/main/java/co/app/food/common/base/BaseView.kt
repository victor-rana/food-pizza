package co.app.food.common.base

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseView<V : ViewModel, S : ViewState>(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), AppLifecycleDelegate, LifecycleOwner {

    abstract val clazz: Class<V>
    val _state = PublishSubject.create<S>()
    val state = _state.hide()
    val compositeBag = CompositeDisposable()
    abstract val registry: LifecycleRegistry

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    override fun onDestroy() {
        _state.onComplete()
        compositeBag.clear()
    }

    override fun onAppBackgrounded() {
    }

    override fun onAppForegrounded() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onCreate() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }
}
