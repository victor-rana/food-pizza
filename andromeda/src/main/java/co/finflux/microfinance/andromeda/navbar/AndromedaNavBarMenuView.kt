package co.app.food.andromeda.navbar

import android.view.View
import co.app.food.andromeda.OnMenuItemClickListener

/**
 * Defines the behaviour of menu view in the Andromeda navbar. Custom views which need to be added as menu view should implement this behaviour
 */
interface AndromedaNavBarMenuView {

    /**
     * The view that has to be added in the menu region
     */
    val view: View

    fun showFirstMenuItem(menuItem: MenuItem, menuItemClickListener: OnMenuItemClickListener)

    fun showSecondMenuItem(menuItem: MenuItem, menuItemClickListener: OnMenuItemClickListener)

    fun addFirstMenuBadge(badgeText: String)

    fun addSecondMenuBadge(badgeText: String)

    fun hideMenu()

    fun hideFirstMenuBadge()

    fun hideSecondMenuBadge()
}
