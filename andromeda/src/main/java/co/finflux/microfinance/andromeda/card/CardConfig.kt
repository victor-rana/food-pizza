package co.app.food.andromeda.card

import android.view.View
import android.view.ViewGroup
import androidx.core.view.animation.PathInterpolatorCompat
import co.app.food.andromeda.card.internal.AnimationConfig
import co.app.food.andromeda.card.internal.AnimationInfo

private val shortAnimation = AnimationInfo(
    PathInterpolatorCompat.create(0.34f, 1f, 0.26f, 0.99f),
    1.0f
)

private val longAnimation = AnimationInfo(
    PathInterpolatorCompat.create(0.39f, 1.24f, 0.26f, 0.99f),
    1.0f
)

sealed class CardConfig(
    val contentView: View,
    val cardParentView: ViewGroup,
    internal val animationConfig: AnimationConfig,
    internal val isModal: Boolean
) {
    internal fun isNonDismissibleNotchCard() = this is NonDismissibleNotchCardConfig ||
        this is ResizableNonDismissibleNotchCardConfig

    internal fun isResizableCard() = this !is NonDismissibleNotchCardConfig &&
        this !is DismissibleNotchCardConfig

    internal fun isNotchCard() = this is NotchCardConfig
}

class DialogCardConfig(
    view: View,
    parentView: ViewGroup
) : CardConfig(
    view,
    parentView,
    AnimationConfig(
        shortAnimation,
        shortAnimation
    ),
    true
)

class FixedCardConfig(
    view: View,
    parentView: ViewGroup,
    isModal: Boolean = true
) : CardConfig(
    view,
    parentView,
    AnimationConfig(
        shortAnimation,
        shortAnimation
    ),
    isModal
)

sealed class NotchCardConfig(
    view: View,
    parentView: ViewGroup,
    var snapPoints: List<Float>,
    var startSnapPointIndex: Int,
    animationConfig: AnimationConfig,
    isModal: Boolean
) : CardConfig(
    view,
    parentView,
    animationConfig,
    isModal
)

class DismissibleNotchCardConfig(
    view: View,
    parentView: ViewGroup,
    snapPoints: List<Float>,
    cardStartSnapPointIndex: Int
) : NotchCardConfig(
    view,
    parentView,
    snapPoints,
    cardStartSnapPointIndex,
    AnimationConfig(
        longAnimation,
        shortAnimation
    ),
    true
)

class NonDismissibleNotchCardConfig(
    view: View,
    parentView: ViewGroup,
    snapPoints: List<Float>,
    cardStartSnapPointIndex: Int
) : NotchCardConfig(
    view,
    parentView,
    snapPoints,
    cardStartSnapPointIndex,
    AnimationConfig(
        shortAnimation,
        longAnimation
    ),
    false
)

sealed class ResizableNotchCardConfig(
    view: View,
    parentView: ViewGroup,
    snapPoints: List<Float>,
    startSnapPointIndex: Int,
    animationConfig: AnimationConfig,
    isModal: Boolean
) : NotchCardConfig(
    view,
    parentView,
    snapPoints,
    startSnapPointIndex,
    animationConfig,
    isModal
)

class ResizableDismissibleNotchCardConfig(
    view: View,
    parentView: ViewGroup,
    snapPoints: List<Float>,
    cardStartSnapPointIndex: Int
) : ResizableNotchCardConfig(
    view,
    parentView,
    snapPoints,
    cardStartSnapPointIndex,
    AnimationConfig(
        longAnimation,
        shortAnimation
    ),
    true
)

class ResizableNonDismissibleNotchCardConfig(
    view: View,
    parentView: ViewGroup,
    snapPoints: List<Float>,
    cardStartSnapPointIndex: Int
) : ResizableNotchCardConfig(
    view,
    parentView,
    snapPoints,
    cardStartSnapPointIndex,
    AnimationConfig(
        shortAnimation,
        longAnimation
    ),
    false
)
