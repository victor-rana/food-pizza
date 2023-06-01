package co.app.food.andromeda

import android.app.Activity
import android.view.LayoutInflater
import co.app.food.andromeda.card.AndromedaCard
import co.app.food.andromeda.card.AndromedaCardFactory
import co.app.food.andromeda.card.CardEventListener
import co.app.food.andromeda.databinding.LayoutDialogLoadingBinding
import co.app.food.andromeda.extensions.makeVisible

typealias LoadingCardDismissListener = () -> Unit

object LoadingDialog {
    fun show(
        activity: Activity,
        cardDismissListener: LoadingCardDismissListener = {}
    ): AndromedaCard {
        val cardBinding =
            LayoutDialogLoadingBinding.inflate(LayoutInflater.from(activity))
        return generateFixedCard(activity, cardBinding, cardDismissListener)
    }

    fun showWithText(
        activity: Activity,
        message: String,
        cardDismissListener: LoadingCardDismissListener = {}
    ): AndromedaCard {
        val cardBinding =
            LayoutDialogLoadingBinding.inflate(LayoutInflater.from(activity))
        cardBinding.loadingText.text = message
        cardBinding.loadingText.makeVisible()
        return generateFixedCard(activity, cardBinding, cardDismissListener)
    }

    private fun generateFixedCard(
        activity: Activity,
        binding: LayoutDialogLoadingBinding,
        cardDismissListener: LoadingCardDismissListener
    ): AndromedaCard {
        return AndromedaCardFactory.getFixedCard(activity, binding.root).apply {
            show()
            cardEventListener = object : CardEventListener {
                override fun onCardDismiss(isUserAction: Boolean) {
                    super.onCardDismiss(isUserAction)
                    cardDismissListener()
                }
            }
        }
    }
}
