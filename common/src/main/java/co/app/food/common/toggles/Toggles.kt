package co.app.food.common.toggles

object General : ToggleEntity {
    override fun getToggles() = listOf<Toggle>()
}

object NetworkMock : ToggleEntity {
    override fun getToggles() = listOf(
        Toggle(KEY_MOCK_LOGIN, true)
    )

    const val KEY_MOCK_LOGIN = "is_mock_login_toggle"
}
