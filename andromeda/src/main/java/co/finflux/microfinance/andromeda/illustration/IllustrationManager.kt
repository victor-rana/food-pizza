package co.app.food.andromeda.illustration

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.illustration.Illustration
import co.app.food.andromeda.extensions.getDrawableCompat
import co.app.food.andromeda.illustration.IllustrationManager.getIllustrationDrawable
import java.util.*

/**
 * Andromeda Illustration Manager
 *
 * [getIllustrationDrawable] can be used to get the illustration vector drawable according to the current region.
 *
 * [getIllustrationBitmap] can be used to get the illustration drawable in bitmap form according to the current region.
 *
 * @see [Illustration]
 *
 * NOTE: Write the following code in your activity's onCreate to support backward compatibility of vector drawable.
 * ```
 * AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
 * ```
 * You can read more about this here : <https://developer.android.com/reference/android/support/v7/app/AppCompatDelegate#setcompatvectorfromresourcesenabled>
 *
 */
object IllustrationManager {

    /**
     * @param context: Context of the activity
     * @param illustrationName: Name of the illustration needed. Has to be of of [Illustration] type
     * @param callback: Callback in which you will get the drawable
     *
     */
    fun getIllustrationDrawable(
        context: Context,
        illustrationName: Illustration,
        callback: (Drawable) -> Unit
    ) {
        callback.invoke(getDefaultDrawable(context, illustrationName))
    }

    /**
     * @param context: Context of the activity
     * @param illustrationName: Name of the illustration needed. Has to be of of [Illustration] type
     * @param callback: Callback in which you will get the bitmap
     *
     */
    fun getIllustrationBitmap(
        context: Context,
        illustrationName: Illustration,
        callback: (Bitmap) -> Unit
    ) {
        getIllustrationDrawable(
            context,
            illustrationName
        ) { illustrationDrawable -> callback.invoke(getBitmap(illustrationDrawable)) }
    }

    private fun getDefaultDrawable(context: Context, illustrationName: Illustration): Drawable {
        val drawableResourceId = getDrawableResId(context, getFullAssetNamePair(illustrationName))
        return if (drawableResourceId != 0) {
            AppCompatResources.getDrawable(context, drawableResourceId)
                ?: context.getDrawableCompat(R.drawable.andromeda_illustration_placeholder)!!
        } else {
            // Return placeholder image
            context.getDrawableCompat(R.drawable.andromeda_illustration_placeholder)!!
        }
    }

    private fun getBitmap(vectorDrawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

    private fun getDrawableResId(context: Context, drawableNamePair: Pair<String, String>): Int {
        val regionDrawableName = drawableNamePair.first
        val defaultDrawableName = drawableNamePair.second
        val regionDrawable =
            context.resources.getIdentifier(regionDrawableName, "drawable", context.packageName)
        return if (regionDrawable == 0) {
            context.resources.getIdentifier(defaultDrawableName, "drawable", context.packageName)
        } else {
            regionDrawable
        }
    }

    private fun getFullAssetNamePair(illustrationName: Illustration): Pair<String, String> {
        return Pair(
            (illustrationName.toString()).lowercase(Locale.ROOT),
            (illustrationName.toString()).lowercase(Locale.ROOT)
        )
    }
}
