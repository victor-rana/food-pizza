package co.app.food.andromeda.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun View.makeVisible(animate: Boolean = false) {
    if (animate) {
        animate().alpha(1f).setDuration(3000).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })
    } else {
        visibility = View.VISIBLE
    }
}

fun View.makeGone(animate: Boolean = false) {
    hide(View.GONE, animate)
}

fun View.makeInvisible(animate: Boolean = false) {
    hide(View.INVISIBLE, animate)
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}

fun View.isInvisible(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun makeViewsVisible(vararg views: View) {
    views.forEach { it.makeVisible() }
}

fun makeViewsInvisible(vararg views: View) {
    views.forEach { it.makeInvisible() }
}

private fun View.hide(hidingStrategy: Int, animate: Boolean = true) {
    if (animate) {
        animate().alpha(0f).setDuration(300).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = hidingStrategy
            }
        })
    } else {
        visibility = hidingStrategy
    }
}

fun ViewBinding.show() {
    this.root.makeVisible()
}

fun ViewBinding.hide() {
    this.root.makeGone()
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}
