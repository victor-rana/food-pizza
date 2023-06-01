package co.app.food.andromeda

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class CommonImageLoaderFactory(
    private val context: Context,
    private val builder: ImageLoader.Builder.() -> Unit = {}
) : ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
            .availableMemoryPercentage(0.25)
            .allowHardware(false)
            .crossfade(true)
            .okHttpClient {
                val cacheControlInterceptor = Interceptor { chain ->
                    chain.proceed(chain.request())
                        .newBuilder()
                        .header("Cache-Control", "max-age=3600,public")
                        .build()
                }
                // Don't limit concurrent network requests by host.
                val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }

                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(context))
                    .dispatcher(dispatcher)
                    .addNetworkInterceptor(cacheControlInterceptor)
                    .build()
            }
            .apply(builder)
            .build()
    }
}
