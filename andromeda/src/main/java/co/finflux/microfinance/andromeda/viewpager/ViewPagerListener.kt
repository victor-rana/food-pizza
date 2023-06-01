package co.app.food.andromeda.viewpager

import android.view.View
import co.app.food.andromeda.databinding.AndromedaLayoutViewPagerItemsBinding

interface ViewPagerListener {
    fun onPageLoad(position: Int, urlToLoad: String, binding: AndromedaLayoutViewPagerItemsBinding)
    fun onPageDestroy(position: Int, view: View)
    fun onPageSelected(position: Int, urlToLoad: String)
}
