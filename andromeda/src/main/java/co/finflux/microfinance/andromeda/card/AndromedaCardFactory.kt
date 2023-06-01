package co.app.food.andromeda.card

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import co.app.food.andromeda.card.AndromedaCardSnapPoints.NO_SNAP_POINT

/**
 * Factory class to easily creating [AndromedaCard] by passing bare minimum information
 */
class AndromedaCardFactory {

    companion object {
        /**
         * @param activity
         * Activity in which the dialog has to be displayed
         *
         * @param contentView
         * Content to be displayed in Dialog
         *
         * @return An [AndromedaCard] that is of type Dialog
         */
        fun getDialogCard(
            activity: Activity,
            contentView: View
        ): AndromedaCard {
            return getDialogCard(
                activity.findViewById<ViewGroup>(android.R.id.content),
                contentView
            )
        }

        /**
         * @param container
         * A [android.view.ViewGroup] in which the dialog has to be added
         *
         * @param contentView
         * Content to be displayed in Dialog
         *
         * @return An [AndromedaCard] that is of type Dialog
         */
        fun getDialogCard(
            container: ViewGroup,
            contentView: View
        ): AndromedaCard {
            return AndromedaCard(
                DialogCardConfig(
                    contentView,
                    container
                )
            )
        }

        /**
         * @param activity
         * Activity in which the Fixed Card has to be displayed
         *
         * @param contentView
         * Content to be displayed in Fixed Card
         *
         * @param isModal
         * Indicates whether the Fixed Card has modal nature or not. Value is <code>true</code> by default
         *
         * @return An [AndromedaCard] that is of type Fixed
         */
        fun getFixedCard(
            activity: Activity,
            contentView: View,
            isModal: Boolean = true
        ): AndromedaCard {
            return getFixedCard(
                activity.findViewById<ViewGroup>(android.R.id.content),
                contentView,
                isModal
            )
        }

        /**
         * @param container
         * A [android.view.ViewGroup] in which the Fixed Card has to be added
         *
         * @param contentView
         * Content to be displayed in Fixed Card
         *
         * @param isModal
         * Indicates whether the Fixed Card has modal nature or not. Value is <code>true</code> by default
         *
         * @return An [AndromedaCard] that is of type Fixed
         */
        fun getFixedCard(
            container: ViewGroup,
            contentView: View,
            isModal: Boolean = true
        ): AndromedaCard {
            return AndromedaCard(
                FixedCardConfig(
                    contentView,
                    container,
                    isModal
                )
            )
        }

        /**
         * @param activity
         * Activity in which the Notch Card has to be displayed
         *
         * @param contentView
         * Content to be displayed in Notch Card
         *
         * @param snapPoints
         * Points between Expanded(100%) and Collapsed(0%) to which card will be snapped when swipe is finished.
         * Values expected are percentages between 0.0 and 1.0
         *
         * @param cardStartSnapPointIndex
         * DismissibleNotchCard when created will expand all the way to the top, but if it has to be start from some other point, add it a snap point and use the index value of it in the list as  value for this parameter
         * Eg. if your snap points are listOf(0.2f, 0.4f, 0.6f) and you want NotchCard to start from 0.4, pass cardStartSnapPointIndex as 1. Like any other index value it starts from 0
         *
         * @param resizable
         * Its false by default, which is what enables notch card to go all the way to top. But if the notch card need to have height based on the height of the content, set it to true
         *
         * @return An [AndromedaCard] that is of type Dismissible Notch
         */
        fun getDismissibleNotchCard(
            activity: Activity,
            contentView: View,
            snapPoints: List<Float> = emptyList(),
            cardStartSnapPointIndex: Int = NO_SNAP_POINT,
            resizable: Boolean = false
        ): AndromedaCard {
            return getDismissibleNotchCard(
                activity.findViewById<ViewGroup>(android.R.id.content),
                contentView,
                snapPoints,
                cardStartSnapPointIndex,
                resizable
            )
        }

        /**
         * @param container
         * A [android.view.ViewGroup] in which the Notch Card has to be added
         *
         * @param contentView
         * Content to be displayed in Notch Card
         *
         * @param snapPoints
         * Points between Expanded(100%) and Collapsed(0%) to which card will be snapped when swipe is finished.
         * Values expected are percentages between 0.0 and 1.0
         *
         * @param cardStartSnapPointIndex
         * DismissibleNotchCard when created will expand all the way to the top, but if it has to be start from some other point, add it a snap point and use the index value of it in the list as  value for this parameter
         * Eg. if your snap points are listOf(0.2f, 0.4f, 0.6f) and you want NotchCard to start from 0.4, pass cardStartSnapPointIndex as 1. Like any other index value it starts from 0
         * If no value is passed for this field it will start from (1.0) which is expanded state
         *
         * @param resizable
         * Its false by default, which is what enables notch card to go all the way to top. But if the notch card need to have height based on the height of the content, set it to true
         *
         * @return An [AndromedaCard] that is of type Dismissible Notch
         */
        fun getDismissibleNotchCard(
            container: ViewGroup,
            contentView: View,
            snapPoints: List<Float> = emptyList(),
            cardStartSnapPointIndex: Int = NO_SNAP_POINT,
            resizable: Boolean = false
        ): AndromedaCard {

            return AndromedaCard(
                if (resizable) {
                    ResizableDismissibleNotchCardConfig(
                        contentView,
                        container,
                        snapPoints,
                        cardStartSnapPointIndex
                    )
                } else {
                    DismissibleNotchCardConfig(
                        contentView,
                        container,
                        snapPoints,
                        cardStartSnapPointIndex
                    )
                }
            )
        }

        /**
         * @param activity
         * Activity in which the Notch Card has to be displayed
         *
         * @param contentView
         * Content to be displayed in Notch Card
         *
         * @param snapPoints
         * Points between Expanded(100%) and 0%(collapsed) to which card will be snapped when swipe is finished. The lowest value in this list will be treated as the peek height of the card
         * Peek height is the lowest point to which the card can be moved (collapsed).
         * Values expected are percentages between 0.0 and 1.0
         * Passing an empty list for this field will result in IllegalArgumentException. A non-empty list with at least 1 value is required.
         *
         * @param cardStartSnapPointIndex
         * DismissibleNotchCard when created will expand all the way to the top, but if it has to be start from some other point, add it a snap point and use the index value of it in the list as  value for this parameter
         * Eg. if your snap points are listOf(0.2f, 0.4f, 0.6f) and you want NotchCard to start from 0.4, pass cardStartSnapPointIndex as 1. Like any other index value it starts from 0.
         * If no value is passed, it will start from the lowest snap point,0.2f in the above case.
         *
         * @param resizable
         * Its false by default, which is what enables notch card to go all the way to top. But if the notch card need to have height based on the height of the content, set it to true
         *
         * @return An [AndromedaCard] that is of type Non Dismissible Notch
         */
        fun getNonDismissibleNotchCard(
            activity: Activity,
            contentView: View,
            snapPoints: List<Float>,
            cardStartSnapPointIndex: Int = NO_SNAP_POINT,
            resizable: Boolean = false
        ): AndromedaCard {
            return getNonDismissibleNotchCard(
                activity.findViewById<ViewGroup>(android.R.id.content),
                contentView,
                snapPoints,
                cardStartSnapPointIndex,
                resizable
            )
        }

        /**
         * @param container
         * A [android.view.ViewGroup] in which the Notch Card has to be added
         *
         * @param contentView
         * Content to be displayed in Notch Card
         *
         *
         * @param snapPoints
         * Points between Expanded(100%) and 0%(collapsed) to which card will be snapped when swipe is finished. The lowest value in this list will be treated as the peek height of the card
         * Peek height is the lowest point to which the card can be moved (collapsed).
         * Values expected are percentages between 0.0 and 1.0
         * Passing an empty list for this field will result in IllegalArgumentException. A non-empty list with at least 1 value is required.
         *
         * @param cardStartSnapPointIndex
         * DismissibleNotchCard when created will expand all the way to the top, but if it has to be start from some other point, add it a snap point and use the index value of it in the list as  value for this parameter
         * Eg. if your snap points are listOf(0.2f, 0.4f, 0.6f) and you want NotchCard to start from 0.4, pass cardStartSnapPointIndex as 1. Like any other index value it starts from 0.
         * If no value is passed, it will start from the lowest snap point,0.2f in the above case.
         *
         * @param resizable
         * Its false by default, which is what enables notch card to go all the way to top. But if the notch card need to have height based on the height of the content, set it to true
         *
         * @return An [AndromedaCard] that is of type Non Dismissible Notch
         */
        fun getNonDismissibleNotchCard(
            container: ViewGroup,
            contentView: View,
            snapPoints: List<Float>,
            cardStartSnapPointIndex: Int = NO_SNAP_POINT,
            resizable: Boolean = false
        ): AndromedaCard {

            return AndromedaCard(
                if (resizable) {
                    ResizableNonDismissibleNotchCardConfig(
                        contentView,
                        container,
                        snapPoints,
                        cardStartSnapPointIndex
                    )
                } else {
                    NonDismissibleNotchCardConfig(
                        contentView,
                        container,
                        snapPoints,
                        cardStartSnapPointIndex
                    )
                }
            )
        }
    }
}
