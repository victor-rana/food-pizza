package co.app.food.andromeda.viewpager

import android.view.View
import co.app.food.andromeda.databinding.AndromedaLayoutViewPagerItemsBinding

class DefaultViewPagerAdapter(private val viewPagerListener: ViewPagerListener) :
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
