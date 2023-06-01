package co.app.food.andromeda.card.internal

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.ViewCompat

internal class CardSnapAnimator(
    private val cardLayout: View,
    private val animationConfig: AnimationConfig
) {

    private var animator: Animator? = null

    fun snapToPosition(
        translateFrom: Int,
        translateTo: Int,
        duration: Long,
        doOnStart: () -> Unit = {},
        doOnEnd: () -> Unit = {}
    ) {
        animator = getCardTransitionAnimator(
            translateFrom,
            translateTo,
            duration
        ).apply {
            doOnStart { doOnStart() }
            doOnEnd {
                doOnEnd()
            }
            start()
        }
    }

    private fun getCardTransitionAnimator(
        fromTop: Int,
        toTop: Int,
        timeDuration: Long
    ): Animator {
        var animatedValue: Int
        return ValueAnimator.ofInt(
            fromTop,
            toTop
        ).apply {
            duration = timeDuration
            interpolator = if (fromTop - toTop > 0) {
                animationConfig.upwards.interpolator
            } else {
                animationConfig.downwards.interpolator
            }
            addUpdateListener {
                animatedValue = it.animatedValue as Int
                ViewCompat.offsetTopAndBottom(
                    cardLayout,
                    animatedValue - cardLayout.top
                )
            }
        }
    }
}
