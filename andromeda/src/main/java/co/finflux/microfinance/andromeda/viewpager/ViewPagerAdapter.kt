package co.app.food.andromeda.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import co.app.food.andromeda.databinding.AndromedaLayoutViewPagerItemsBinding

abstract class ViewPagerAdapter : PagerAdapter() {
    private val pages: MutableList<PageItem> = mutableListOf()

    abstract fun loadPage(
        position: Int,
        urlToLoad: String,
        binding: AndromedaLayoutViewPagerItemsBinding
    )

    abstract fun destroyPage(
        position: Int,
        view: View
    )

    override fun getCount(): Int {
        return pages.size
    }

    // Client does not override this for now
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewBinding = AndromedaLayoutViewPagerItemsBinding
            .inflate(LayoutInflater.from(container.context), container, false)
        container.addView(viewBinding.root)
        loadPage(position, binding = viewBinding, urlToLoad = pages[position].urlToLoad)
        return viewBinding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].title
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        destroyPage(position, `object` as View)
        (container as ViewPager).removeView(`object` as View)
    }

    fun addPages(pageItems: List<PageItem>) {
        this.pages.clear()
        this.pages.addAll(pageItems)
        notifyDataSetChanged()
    }

    fun getTab(position: Int): PageItem {
        return pages[position]
    }
}
