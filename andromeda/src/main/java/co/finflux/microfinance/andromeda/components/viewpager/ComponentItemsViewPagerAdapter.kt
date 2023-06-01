package co.app.food.andromeda.components.viewpager

import android.view.View
import co.app.food.andromeda.databinding.AndromedaLayoutViewPagerItemsBinding
import co.app.food.andromeda.viewpager.ViewPagerAdapter
import co.app.food.andromeda.viewpager.ViewPagerListener

class ComponentItemsViewPagerAdapter(private val viewPagerListener: ViewPagerListener) :
    ViewPagerAdapter() {

    override fun loadPage(
        position: Int,
        urlToLoad: String,
        binding: AndromedaLayoutViewPagerItemsBinding
    ) {
        viewPagerListener.onPageLoad(position, urlToLoad, binding)
    }

    override fun destroyPage(position: Int, view: View) {
        viewPagerListener.onPageDestroy(position, view)
    }
}
