package co.app.food.andromeda.navbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.Group
import co.app.food.andromeda.R
import co.app.food.andromeda.extensions.makeGone
import co.app.food.andromeda.extensions.makeVisible
import co.app.food.andromeda.text.AndromedaTextView
import co.app.food.andromeda.text.TypographyStyle

open class DefaultAndromedaNavBarTitleView(context: Context) : AndromedaNavBarTitleView {

    open fun getLayout() = R.layout.andromeda_navbar_title_view

    override val view: View = LayoutInflater.from(context).inflate(getLayout(), null)

    override fun setLogo(drawable: Drawable) {
        view.findViewById<AppCompatImageView>(R.id.navigation_logo).setImageDrawable(drawable)
        view.findViewById<Group>(R.id.navigation_logo_group).makeVisible()
    }

    override fun hideLogo() {
        view.findViewById<Group>(R.id.navigation_logo_group).makeGone()
    }

    override fun setTitle(title: CharSequence) {
        view.findViewById<AndromedaTextView>(R.id.navigation_title).apply {
            makeVisible()
            text = title
        }
    }

    override fun hideTitle() {
        view.findViewById<AndromedaTextView>(R.id.navigation_title).makeGone()
    }

    override fun setSubtitle(subtitle: CharSequence) {
        view.findViewById<AndromedaTextView>(R.id.navigation_subtitle).apply {
            text = subtitle
            makeVisible(true)
        }
        view.findViewById<AndromedaTextView>(R.id.navigation_title).typographyStyle =
            TypographyStyle.TITLE_SMALL_BOLD_DEFAULT
    }

    override fun hideSubtitle() {
        view.findViewById<AndromedaTextView>(R.id.navigation_subtitle).makeGone()
        view.findViewById<AndromedaTextView>(R.id.navigation_title).typographyStyle =
            TypographyStyle.TITLE_MODERATE_BOLD_DEFAULT
    }
}
