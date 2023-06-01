package co.app.food.landing.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import co.app.food.andromeda.compose.AndromedaComposeTheme
import co.app.food.landing.presentation.components.LandingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndromedaComposeTheme {
                LandingScreen()
            }
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, LandingActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
        }
    }
}
