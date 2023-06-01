package co.app.food.landing.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import co.app.food.common.addTo
import co.app.food.common.base.BaseViewModel
import co.app.food.common.singleIo
import co.app.food.landing.model.CrustsItem
import co.app.food.landing.model.LandingScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val useCase: LandingUseCase
) : BaseViewModel() {
    /**
     * The current state of the landing screen. It holds all the information required to render the UI.
     */
    var landingState: LandingScreenState by mutableStateOf(LandingScreenState())
        private set

    private var cartList: ArrayList<CrustsItem> = ArrayList()

    init {
        getData()
    }

    fun getData() {
        useCase.sync()
            .compose(singleIo())
            .subscribe(
                { data ->
                    landingState = landingState.copy(foodItems = data)
                },
                {
                    useCase.trackError(it)
                }
            )
            .addTo(bag)
    }

    fun insertItemCart(cartItems: CrustsItem) {
        cartList.add(cartItems)
        landingState = landingState.copy(
            cartItems = cartList
        )
    }

    fun removeItemCart(cartItems: CrustsItem) {
        cartList.remove(cartItems)
        landingState = landingState.copy(
            cartItems = cartList
        )
    }

    fun trackError(error: Throwable) {
        useCase.trackError(error)
    }
}
