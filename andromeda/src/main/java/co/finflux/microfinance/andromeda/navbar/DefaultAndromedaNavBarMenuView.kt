package co.app.food.andromeda.navbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import co.app.food.andromeda.AndromedaAttributeManager
import co.app.food.andromeda.OnMenuItemClickListener
import co.app.food.andromeda.R
import co.app.food.andromeda.badge.AndromedaBadge
import co.app.food.andromeda.extensions.isVisible
import co.app.food.andromeda.extensions.makeGone
import co.app.food.andromeda.extensions.makeVisible
import co.app.food.andromeda.icons.AndromedaIconView

open class DefaultAndromedaNavBarMenuView(context: Context) : AndromedaNavBarMenuView {

    open fun getLayout() = R.layout.andromeda_navbar_menu_view_layout

    override val view: View = LayoutInflater.from(context).inflate(getLayout(), null)

    override fun showFirstMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        bindMenuItem(
            view.findViewById(R.id.navigation_menu_icon_1),
            menuItem,
            menuItemClickListener
        )
    }

    override fun showSecondMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        bindMenuItem(
            view.findViewById(R.id.navigation_menu_icon_2),
            menuItem,
            menuItemClickListener
        )
    }

    override fun addFirstMenuBadge(badgeText: String) {
        if (view.findViewById<AndromedaIconView>(R.id.navigation_menu_icon_1).isVisible()) {
            view.findViewById<AndromedaBadge>(R.id.icon_1_badge).apply {
                makeVisible()
                setBadgeText(badgeText)
            }
        }
    }

    override fun addSecondMenuBadge(badgeText: String) {
        if (view.findViewById<AndromedaIconView>(R.id.navigation_menu_icon_2).isVisible()) {
            view.findViewById<AndromedaBadge>(R.id.icon_2_badge).apply {
                makeVisible()
                setBadgeText(badgeText)
            }
        }
    }

    override fun hideMenu() {
        view.findViewById<AndromedaIconView>(R.id.navigation_menu_icon_1).makeGone()
        view.findViewById<AndromedaIconView>(R.id.navigation_menu_icon_2).makeGone()
        view.findViewById<AndromedaBadge>(R.id.icon_1_badge).makeGone()
        view.findViewById<AndromedaBadge>(R.id.icon_2_badge).makeGone()
    }

    override fun hideFirstMenuBadge() {
        view.findViewById<AndromedaBadge>(R.id.icon_1_badge).makeGone()
    }

    override fun hideSecondMenuBadge() {
        view.findViewById<AndromedaBadge>(R.id.icon_2_badge).makeGone()
    }

    private fun bindMenuItem(
        menuView: AndromedaIconView,
        menuItem: MenuItem,
        clickListener: OnMenuItemClickListener
    ) {
        menuView.apply {
            setIcon(
                menuItem.icon,
                AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    menuItem.iconColorToken
                )
            )
            contentDescription = menuItem.title
            setOnClickListener { clickListener(menuItem.id) }
            makeVisible()
        }
    }
}
