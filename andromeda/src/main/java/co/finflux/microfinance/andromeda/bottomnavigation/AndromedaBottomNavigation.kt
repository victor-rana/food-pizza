package co.app.food.andromeda.bottomnavigation

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.IntRange
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.databinding.AndromedaBottomNavigationBinding
import co.app.food.andromeda.databinding.AndromedaBottomNavigationTabLayoutBinding
import co.app.food.andromeda.text.TypographyStyle
import co.app.food.andromeda.tokens.icon_dynamic_default
import co.app.food.andromeda.tokens.icon_dynamic_inactive
import co.app.food.andromeda.viewpager.AndromedaViewPager

/**
 * Andromeda BottomNavigation
 *
 * [AndromedaBottomNavigation] display three to five tabs at the bottom of a screen. Each destination is represented by an icon and a text label.
 * When a bottom navigation icon is tapped, the user is taken to the top-level navigation destination associated with that icon and label.
 *
 *
 * ```xml
 *  <co.app.food.andromeda.bottomnavigation.AndromedaBottomNavigation
 *      android:id="@+id/bottom_navigation_view"
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"/>
 *```
 *
 * Kotlin Usage:
 *
 * ```kotlin
 *  val tabData = ArrayList<AndromedaBottomNavigation.TabData>(5)
 *  tabData.add(AndromedaBottomNavigation.TabData("Notes", Icon.ACTIONS_16_ADD_NOTES_PENCIL, fill_active_primary, "This is Notes"))
 *  tabData.add(AndromedaBottomNavigation.TabData("Favorites", Icon.ACTIONS_16_ADD_TO_FAVORITES, fill_error_primary, "This is Favorites"))
 *  tabData.add(AndromedaBottomNavigation.TabData("Calendar", Icon.ACTIONS_16_CALENDAR, fill_active_primary, "This is Calendar"))
 *
 * // Add to bottom navigation view
 * bottom_navigation_view.addTabs(tabData)
 *
 * // Set default selected tab
 * bottom_navigation_view.setSelectedTab(3)

 * ```
 * [addTabs] allows to send an ArrayList of TabData. Minimum number of tabs allowed is 3 and maximum number of tabs allowed is 5.
 * This method sets tab 1 as selected by default
 *
 * [setSelectedTab] takes position from 0 to 4 and sets the tab at that position as selected.
 *
 */

private const val TAG = "AndromedaBottomNavigation"

class AndromedaBottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    /**
     * [text] sets the text for tabs
     * [iconName] sets the icon for the tab.
     * [iconColorToken] sets the color token of the icon. This value can be null and default color will be set to the icon in the selected state.
     * [contentDescription] is for a11y. By default screen reader will read out the the text of the tab. If that has to be changed,
     * the desired text can be passed to this field. So when screen reader is focused on tab containing this data, it will read out value passed instead the value of text
     */
    data class TabData(
        val text: String,
        val iconName: Icon,
        val iconColorToken: Int?,
        val contentDescription: String = text
    )

    private val MIN_TABS = 3
    private val MAX_TABS = 5
    private lateinit var tabData: ArrayList<TabData>
    private var selectedTab = 0
    private var onTabChangeListener: OnTabChangeListener? = null
    private var selectedIconColorToken: Int = icon_dynamic_default
    private var unselectedIconColorToken: Int = icon_dynamic_inactive
    private var viewPager: AndromedaViewPager? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.andromeda_bottom_navigation, this, true)
    }

    /**
     * Set selected tab position
     * @param tabPosition is used to set the position of selected tab. This value should always be from 0 to 4.
     */
    fun setSelectedTab(@IntRange(from = 0, to = 4) tabPosition: Int) {
        // setListener
        if (tabPosition == selectedTab) {
            onTabChangeListener?.onTabReselected(selectedTab)
        } else {
            onTabChangeListener?.onTabUnselected(selectedTab)
            selectedTab = tabPosition
            onTabChangeListener?.onTabSelected(selectedTab)
        }

        viewPager?.setCurrentItem(selectedTab, true)

        // setStyle
        val bottomNavigationBinding: AndromedaBottomNavigationBinding =
            AndromedaBottomNavigationBinding.bind(findViewById(R.id.bottom_navigation_container))

        var colorToken: Int
        var typographyStyle: TypographyStyle

        for (i in tabData.indices) {
            val view = bottomNavigationBinding.bottomTabs.findViewWithTag<View>(i)

            if (i == tabPosition) {
                colorToken = if (tabData[i].iconColorToken == null) {
                    selectedIconColorToken
                } else {
                    tabData[i].iconColorToken!!
                }
                typographyStyle = TypographyStyle.CAPTION_MODERATE_DEMI_DEFAULT
                view.isSelected = true
            } else {
                colorToken = unselectedIconColorToken
                typographyStyle = TypographyStyle.CAPTION_MODERATE_BOOK_DEFAULT
                view.isSelected = false
            }

            val tabBinding: AndromedaBottomNavigationTabLayoutBinding =
                AndromedaBottomNavigationTabLayoutBinding.bind(view)
            tabBinding.bottomNavigationTabIcon.setIcon(tabData[i].iconName, colorToken)
            tabBinding.bottomNavigationTabTitle.typographyStyle = typographyStyle
        }
    }

    /**
     * Set tabs for AndromedaBottomNavigation component. Also sets tab 1 as selected.
     * @param tabsData takes an arrayList of min size 3 and max size 5, of TabData.
     */
    fun addTabs(tabsData: ArrayList<TabData>) {
        tabData = tabsData

        if (tabsData.size > MAX_TABS || tabsData.size < MIN_TABS) {
            throw IllegalArgumentException("Minimum 3 and maximum 5 tabs allowed")
        }

        val bottomNavigationBinding: AndromedaBottomNavigationBinding =
            AndromedaBottomNavigationBinding.bind(findViewById(R.id.bottom_navigation_container))

        val layoutInflater = LayoutInflater.from(context)
        bottomNavigationBinding.bottomTabs.removeAllViews()
        bottomNavigationBinding.bottomTabs.weightSum = tabData.size.toFloat()

        for (i in tabData.indices) {
            val tabBinding: AndromedaBottomNavigationTabLayoutBinding =
                AndromedaBottomNavigationTabLayoutBinding.inflate(layoutInflater, this, false)

            tabBinding.bottomNavigationTabTitle.text = tabData[i].text
            tabBinding.bottomNavigationTabIcon.setIcon(
                tabData[i].iconName,
                unselectedIconColorToken
            )
            tabBinding.bottomNavigationTabLayout.contentDescription = tabData[i].contentDescription

            val param = LinearLayout.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT,
                1f
            )
            param.gravity = Gravity.CENTER
            tabBinding.bottomNavigationTabLayout.layoutParams = param

            // add view to bottom navigation
            bottomNavigationBinding.bottomTabs.addView(tabBinding.bottomNavigationTabLayout)

            tabBinding.bottomNavigationTabLayout.tag = i
            addAnimation(tabBinding.bottomNavigationTabLayout, tabBinding.bottomNavigationTabIcon)
            setTabClickListener(tabBinding.bottomNavigationTabLayout)
        }

        // default selected tab = 0
        setSelectedTab(selectedTab)
    }

    /**
     * Register a callback to be invoked when user is interacted with the tabs.
     * When you no longer need the callbacks to be invoked, call this method with a [null] value
     *
     * @param listener The listener that will be invoked when user interacts with tabs.
     */
    fun setOnTabChangeListener(listener: OnTabChangeListener?) {
        onTabChangeListener = listener
    }

    fun setupViewPager(viewPager: AndromedaViewPager) {
        this.viewPager = viewPager
    }

    private fun downAnimation(view: View) {
        val scaleAnimation = ValueAnimator.ofFloat(1f, 0.9f)
        scaleAnimation.duration = 50
        scaleAnimation.addUpdateListener {
            val value = it.animatedValue as Float
            view.scaleX = value
            view.scaleY = value
        }
        scaleAnimation.start()
    }

    private fun upAnimation(view: View) {
        val scaleAnimation1 = ValueAnimator.ofFloat(0.9f, 1.1f)
        scaleAnimation1.duration = 167
        scaleAnimation1.addUpdateListener {
            val value = it.animatedValue as Float
            view.scaleX = value
            view.scaleY = value
        }
        val scaleAnimation2 = ValueAnimator.ofFloat(1.1f, 1f)
        scaleAnimation2.duration = 167
        scaleAnimation2.addUpdateListener {
            val value = it.animatedValue as Float
            view.scaleX = value
            view.scaleY = value
        }
        val scaleAnimation = AnimatorSet()
        scaleAnimation.playSequentially(scaleAnimation1, scaleAnimation2)
        scaleAnimation.start()
    }

    private fun setTabClickListener(tabView: View) {
        tabView.setOnClickListener {
            setSelectedTab(tabView.tag as Int)
        }
    }

    private fun addAnimation(tabView: View, iconView: View) {
        tabView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downAnimation(iconView)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    upAnimation(iconView)
                    tabView.performClick()
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    upAnimation(iconView)
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
    }
}
