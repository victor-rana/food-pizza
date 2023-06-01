package co.app.food.andromeda

import android.app.Activity
import android.view.LayoutInflater
import co.app.food.andromeda.card.AndromedaCard
import co.app.food.andromeda.card.AndromedaCardFactory
import co.app.food.andromeda.card.CardEventListener
import co.app.food.andromeda.databinding.LayoutDialogErrorBinding

object ErrorDialog {

    fun show(
        activity: Activity,
        title: String,
        description: String,
        cta: String = "",
        cardDismissListener: () -> Unit = {}
    ): AndromedaCard {
        val cardBinding =
            LayoutDialogErrorBinding.inflate(LayoutInflater.from(activity))
        val card = generateCard(activity, cardBinding, cardDismissListener)
        cardBinding.apply {
            this.title.text = title
            this.description.text = description
            this.cta.text = cta
            this.cta.setOnClickListener {
                card.dismiss()
            }
        }
        return card
    }

    private fun generateCard(
        activity: Activity,
        binding: LayoutDialogErrorBinding,
        cardDismissListener: LoadingCardDismissListener
    ): AndromedaCard {
        return AndromedaCardFactory.getDialogCard(activity, binding.root).apply {
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
