package co.app.food.andromeda.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import androidx.viewpager.widget.ViewPager

class AndromedaViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    var canSwipe = true
    private var mCurrentPagePosition = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val child: View? = getChildAt(mCurrentPagePosition)
        if (child != null) {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h: Int = child.measuredHeight
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun canSwipe(canSwipe: Boolean) {
        this.canSwipe = canSwipe
    }

    fun setViewPagerReMeasureOnSwipe() {
        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                reMeasureCurrentPage(this@AndromedaViewPager.currentItem)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun reMeasureCurrentPage(position: Int) {
        mCurrentPagePosition = position
        requestLayout()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (canSwipe) {
            super.onTouchEvent(event)
        } else false
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (canSwipe) {
            super.onInterceptTouchEvent(event)
        } else false
    }
}
