package co.app.food.andromeda.card.internal

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import android.view.View.*
import android.view.animation.LinearInterpolator

internal class DialogCardDismissButtonAnimator {

    private var animator: Animator? = null

    fun animateSlideUp(view: View) {
        view.alpha = 0f
        val scaleOverShoot = AnimatorSet().apply {
            playTogether(
                getOvershootScaleAnimator(view, SCALE_X),
                getOvershootScaleAnimator(view, SCALE_Y)
            )
            duration = 250
            interpolator = LinearInterpolator()
        }

        val scaleNormal = AnimatorSet().apply {
            playTogether(
                getAnimator(
                    view,
                    SCALE_X,
                    1f
                ),
                getAnimator(
                    view,
                    SCALE_Y,
                    1f
                )
            )
            duration = 83
            interpolator = LinearInterpolator()
        }

        val scaleAndFadeIn = AnimatorSet().apply {
            playTogether(
                fadeInAnimator(view).apply {
                    duration = 150
                    startDelay = 100
                },
                scaleOverShoot
            )
        }

        animator = AnimatorSet().apply {
            playSequentially(scaleAndFadeIn, scaleNormal)
        }.also { it.start() }
    }

    fun animateSlideDown(view: View) {

        animator = AnimatorSet().apply {
            playTogether(
                getAnimator(
                    view,
                    SCALE_X,
                    0f
                ).apply {
                    duration = 167
                },
                getAnimator(
                    view,
                    SCALE_Y,
                    0f
                ).apply {
                    duration = 167
                },
                getAnimator(
                    view,
                    ALPHA,
                    0f
                ).apply {
                    duration = 83
                }
            )

            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }

    private fun getOvershootScaleAnimator(
        view: View,
        property: Property<View, Float>
    ): Animator {
        return getAnimator(
            view,
            property,
            0f,
            1.1f
        )
    }

    private fun getAnimator(
        view: View,
        property: Property<View, Float>,
        vararg values: Float
    ): Animator {
        return ObjectAnimator.ofFloat(
            view,
            property,
            *values
        )
    }

    private fun fadeInAnimator(view: View): Animator {
        return ObjectAnimator.ofFloat(
            view,
            ALPHA,
            1f
        ).apply {
            startDelay = 100
            duration = 150
            interpolator = LinearInterpolator()
        }
    }
}
