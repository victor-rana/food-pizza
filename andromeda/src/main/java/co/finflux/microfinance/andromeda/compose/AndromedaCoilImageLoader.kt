package co.app.food.andromeda.compose

import android.content.Context
import co.app.food.andromeda.CommonImageLoaderFactory
import coil.ImageLoader
import coil.ImageLoaderFactory

/**
 * Provides a custom image loader that uses the [CommonImageLoaderFactory] to build the default loading settings.
 *
 * */
internal object AndromedaCoilImageLoader {

    /**
     * Loads images in the app, with our default settings.
     * */
    private var imageLoader: ImageLoader? = null

    /**
     * Provides an [ImageLoader] using our custom [ImageLoaderFactory].
     * */
    private var imageLoaderFactory: ImageLoaderFactory? = null

    /**
     * Returns either the currently available [ImageLoader] or builds a new one.
     *
     * @param context - The [Context] to build the [ImageLoader] with.
     * @return - [ImageLoader] that loads images in the app.
     * */
    internal fun imageLoader(context: Context): ImageLoader = imageLoader ?: newImageLoader(context)

    /**
     * Builds a new [ImageLoader] using the given Android [Context]. If the loader already exists, we return it.
     *
     * @param context - The [Context] to build the [ImageLoader] with.
     * @return - [ImageLoader] that loads images in the app.
     * */
    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        imageLoader?.let { return it }

        val imageLoaderFactory = imageLoaderFactory ?: newImageLoaderFactory(context)
        return imageLoaderFactory.newImageLoader().apply {
            imageLoader = this
        }
    }

    /**
     * Uses Android [Context] to build a new [StreamImageLoaderFactory].
     *
     * @param context - The context to load the factory with.
     * @return - [ImageLoaderFactory] that loads all the images in the app.
     * */
    private fun newImageLoaderFactory(context: Context): ImageLoaderFactory {
        return CommonImageLoaderFactory(context).apply {
            imageLoaderFactory = this
        }
    }
}
