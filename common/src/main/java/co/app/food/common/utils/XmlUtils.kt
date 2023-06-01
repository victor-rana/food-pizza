package co.app.food.common.utils

import android.content.Context
import androidx.annotation.XmlRes
import com.google.firebase.remoteconfig.internal.DefaultsXmlParser

class XmlUtils {

    fun getDefaultsFromXml(
        context: Context,
        @XmlRes resourceId: Int
    ): MutableMap<String, String> {
        return DefaultsXmlParser.getDefaultsFromXml(context, resourceId)
    }
}
