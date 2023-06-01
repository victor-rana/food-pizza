package co.app.food.common.toggles.rescue

import co.app.food.common.toggles.NetworkMock.KEY_MOCK_LOGIN

class StopgapToggles {

    fun getToggleState(toggle: String): ToggleState = when (toggle) {
        KEY_MOCK_LOGIN -> ToggleState.UseDefault
        else -> ToggleState.UseDefault
    }
}
