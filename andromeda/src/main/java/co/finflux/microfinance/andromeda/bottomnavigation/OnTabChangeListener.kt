package co.app.food.andromeda.bottomnavigation

/**
 * Listener interface for listening to user tab interactions.
 */
interface OnTabChangeListener {

    /**
     * This callback is invoke when user selects a tab that is different from previously selected tab
     *
     * @param position is the index value of the newly selected tab
     */
    fun onTabSelected(position: Int) {}

    /**
     * This callback is invoke when user selects a tab that is already selected
     *
     * @param position is the index value of the selected tab
     */
    fun onTabReselected(position: Int) {}

    /**
     * This callback is invoke when user selects a tab that is different from previously selected tab.
     * Whenever [onTabSelected] is invoked this callback should also be invoked.
     *
     * @param position is the index value of the tab that was unselected to select new tab
     */
    fun onTabUnselected(position: Int) {}
}
