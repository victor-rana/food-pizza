package co.app.food.andromeda.components.viewpager

import android.annotation.SuppressLint
import co.app.food.andromeda.ComposableHolder
import co.app.food.andromeda.R
import co.app.food.andromeda.viewpager.AndromedaViewPager
import co.app.food.andromeda.viewpager.PageItem
import co.app.food.andromeda.viewpager.ViewPagerListener
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class ViewPagerComponent : EpoxyModelWithHolder<ViewPagerComponent.Holder>() {

    @EpoxyAttribute
    lateinit var pages: List<PageItem>

    @EpoxyAttribute
    lateinit var viewPagerListener: ViewPagerListener

    override fun getDefaultLayout(): Int {
        return R.layout.andromeda_viewpager
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        val viewPagerAdapter = ComponentItemsViewPagerAdapter(viewPagerListener)
        viewPagerAdapter.addPages(pages)
        holder.viewPager.adapter = viewPagerAdapter
    }

    class Holder : ComposableHolder() {
        val viewPager by bind<AndromedaViewPager>(R.id.viewPager)
    }
}
