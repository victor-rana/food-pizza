package co.app.food.andromeda.navbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import co.app.food.andromeda.AndromedaAttributeManager
import co.app.food.andromeda.OnMenuItemClickListener
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.badge.AndromedaBadge
import co.app.food.andromeda.button.AndromedaCircularButton
import co.app.food.andromeda.button.AndromedaCircularButton.CircularButtonType
import co.app.food.andromeda.extensions.*
import co.app.food.andromeda.icons.IconData
import co.app.food.andromeda.tokens.icon_dynamic_default

/*
* Andromeda NavBar Component (Transparent Style)
*
* xml usage
* ```xml
* <co.app.food.andromeda.navbar.AndromedaTransparentNavBar
*  android:layout_width="match_parent"
*  android:layout_height="wrap_content"
*  />
*  ```
*  AndromedaTransparentNavBar can be created in java/kotlin classes like any other view component
*  constructors are identical to standard views, no extra params in constructor
*
*  AndromedaTransparentNavBar doesn't show title, subtitle and logo
*
*  For showing back navigation icon and listening to the click of it
*  [showNavigationIcon] cab be used
*
*  [inflateMenu] method can be used to show menu
*
*  * id attribute of the menu item is used with android namespace, not app
*  * icon_name and icon_color_token properties are used with app name space, not android
*    eg:
*    ```xml
*      <menu>
*         <item
*            android:id="@+id/some_id"
*            app:icon_name="icon_name"
*            app:icon_color_token="icon_color_token"/>
*      </menu>
*    ```
*
*  Contextual action is also not supported in this style
*
*/

class AndromedaTransparentNavBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : AndromedaAbstractNavBar(
    context,
    attributeSet,
    defStyleRes
) {
    private lateinit var navigationBack: AndromedaCircularButton
    private lateinit var navigationMenuIcon1: AndromedaCircularButton
    private lateinit var navigationMenuIcon2: AndromedaCircularButton
    private lateinit var navigationMenuIcon2Badge: AndromedaBadge
    private lateinit var navigationMenuIcon1Badge: AndromedaBadge

    /**
     * @suppress
     */
    override fun initAttributes(attributeSet: AttributeSet) {
        navigationBack = findViewById(R.id.transparent_navigation_back)
        navigationMenuIcon1 = findViewById(R.id.transparent_navigation_menu_icon_1)
        navigationMenuIcon2 = findViewById(R.id.transparent_navigation_menu_icon_2)
        navigationMenuIcon1Badge = findViewById(R.id.icon_1_badge)
        navigationMenuIcon2Badge = findViewById(R.id.icon_2_badge)
        readAttributes(
            attributeSet,
            R.styleable.AndromedaTransparentNavBar
        ) {
            setNavigateBackAccessibilityDescription(
                it.getStringViaResources(
                    R.styleable.AndromedaTransparentNavBar_navigate_back_accessibility_description
                ) ?: resources.getString(R.string.accessibilityNavigateBack)

            )
        }
    }

    /**
     * @suppress
     */
    override fun getLayout() = R.layout.andromeda_navbar_transparent

    override fun getNavigationBackView() = navigationBack

    override fun setNavigationIcon(icon: Icon) {
        navigationBack.apply {
            init(
                CircularButtonType.CIRCULAR_SECONDARY_REGULAR,
                IconData(icon, icon_dynamic_default)
            )
            makeVisible()
        }
    }

    override fun showFirstMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        bindMenuItem(
            navigationMenuIcon1,
            menuItem,
            menuItemClickListener
        )
    }

    override fun showSecondMenuItem(
        menuItem: MenuItem,
        menuItemClickListener: OnMenuItemClickListener
    ) {
        bindMenuItem(
            navigationMenuIcon2,
            menuItem,
            menuItemClickListener
        )
    }

    override fun addFirstMenuBadge(badgeText: String) {
        if (navigationMenuIcon1.isVisible()) {
            navigationMenuIcon1Badge.apply {
                makeVisible()
                setBadgeText(badgeText)
            }
        }
    }

    override fun hideFirstMenuBadge() {
        navigationMenuIcon1Badge.makeGone()
    }

    override fun addSecondMenuBadge(badgeText: String) {
        if (navigationMenuIcon2.isVisible()) {
            navigationMenuIcon2Badge.apply {
                makeVisible()
                setBadgeText(badgeText)
            }
        }
    }

    override fun hideSecondMenuBadge() {
        navigationMenuIcon2Badge.makeGone()
    }

    override fun hideMenu() {
        (navigationMenuIcon1).makeGone()
        (navigationMenuIcon2).makeGone()
        (navigationMenuIcon1Badge).makeGone()
        (navigationMenuIcon2Badge).makeGone()
    }

    /**
     * Provides the view that is displaying the right most menu icon in the navigation bar.
     * Depending on menu(s) are present or not, it could return null or the reference to menu view visible.
     */
    override fun getMenuView1(): View? = findViewById(R.id.navigation_menu_icon_1)

    /**
     * Provides the view that is displaying the right most menu icon in the navigation bar.
     * Depending on menu(s) are present or not, it could return null or the reference to menu view visible.
     */
    override fun getMenuView2(): View? = findViewById(R.id.navigation_menu_icon_2)

    private fun bindMenuItem(
        menuView: AndromedaCircularButton,
        menuItem: MenuItem,
        clickListener: OnMenuItemClickListener
    ) {
        menuView.apply {
            init(
                circularButtonType = CircularButtonType.CIRCULAR_SECONDARY_REGULAR,
                iconData = IconData(
                    menuItem.icon,
                    AndromedaAttributeManager.getColorFromAttribute(
                        context,
                        menuItem.iconColorToken
                    )
                )
            )
            if (menuItem.title != null) setAccessibilityDescription(menuItem.title)
            setOnClickListener { clickListener(menuItem.id) }
            makeVisible()
        }
    }

    fun setNavigateBackAccessibilityDescription(description: String) {
        (navigationBack).setAccessibilityDescription(description)
    }
}
