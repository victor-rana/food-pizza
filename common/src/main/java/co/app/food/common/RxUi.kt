package co.app.food.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function

object RxUi {
    /**
     * Binds some UI function to [Observable]. Usually used in Presenter/ViewModel/etc
     * classes.
     *
     * @param observable not-null source [Observable].
     * @param uiFunc     not-null function that actually performs binding of the [Observable]
     * to something, for example UI.
     * @param <T>        type of [Observable] emission.
     * @return [Disposable] that can be used to dispose and stop bound action.
     </T> */
    fun <T> bind(
        observable: Observable<T>,
        uiFunc: Function<Observable<T>, Disposable>
    ): Disposable {
        return try {
            uiFunc.apply(observable)
        } catch (exception: Throwable) {
            // Because of design error in RxJava v2 "Function" declares that it may throw checked exception.
            // We just wrap it with RuntimeException and throw further.
            throw RuntimeException(exception)
        }
    }

    /**
     * Wraps passed UI action into function that binds [Observable] to UI action on Main
     * Thread.
     *
     * @param uiAction action that performs some UI interaction on Main Thread, like setting text to
     * [android.widget.TextView] and so on.
     * @param <T>      type of [Observable] emission.
     * @return [Function] that can be used to [.bind] [ ] to some UI action.
     </T> */
    fun <T> ui(uiAction: Consumer<T>): Function<Observable<T>, Disposable> {
        return Function { observable: Observable<T> ->
            observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uiAction)
        }
    }
}
