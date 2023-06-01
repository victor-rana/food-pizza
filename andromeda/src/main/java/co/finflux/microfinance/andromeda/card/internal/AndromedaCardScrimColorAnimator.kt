package co.app.food.andromeda.card.internal

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator

internal class AndromedaCardScrimColorAnimator(private val drawable: Drawable) {

    fun fadeIn() {
        getAlphaAnimation(0, 128).start()
    }

    fun fadeOut() {
        getAlphaAnimation(128, 0).start()
    }

    private fun getAlphaAnimation(from: Int, to: Int): Animator {
        return ValueAnimator.ofInt(from, to).apply {
            duration = 100
            interpolator = LinearInterpolator()
            addUpdateListener {
                drawable.alpha = it.animatedValue as Int
            }
        }
    }
}
