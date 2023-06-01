package co.app.food.landing.model

data class LandingScreenState(
    val foodItems: FoodItemDetailsResponse = FoodItemDetailsResponse(
        id = "1",
        name = "Non-Veg Pizza",
        isVeg = false,
        description = "",
        defaultCrust = 1,
        crusts = emptyList(),
    ),
    var cartItems: List<CrustsItem> = emptyList()
)
