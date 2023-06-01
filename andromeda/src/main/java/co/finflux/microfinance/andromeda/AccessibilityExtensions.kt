package co.app.food.andromeda

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat

internal fun makeAnnouncement(
    context: Context,
    clazz: Class<*>,
    accessibilityText: String
) {
    val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE)
        as AccessibilityManager?

    if (accessibilityManager == null || !accessibilityManager.isEnabled) return

    val event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)

    event.apply {
        className = clazz.javaClass.name
        packageName = context.packageName
    }

    event.text.add(accessibilityText)
    accessibilityManager.sendAccessibilityEvent(event)
}

/**
 * To know whether TalkBack is turned on or off
 */
fun Context.isTalkBackOn(): Boolean {
    val talkBackService = "com.google.android.marvin.talkback.TalkBackService"
    val accessibilityManager =
        getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?
    accessibilityManager?.run {
        if (isEnabled) {
            val serviceInfoList =
                getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN)
            serviceInfoList.forEach { service ->
                if (service.resolveInfo?.serviceInfo?.name == talkBackService) {
                    return true
                }
            }
        }
    }
    return false
}

/**
 * Prevents TalkBack from saying "Double tap to activate"
 */
internal fun View.removeClickActionInfo() {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.removeAction(AccessibilityActionCompat.ACTION_CLICK)
                info.isClickable = false
            }
        }
    )
}

/**
 * Changes default click action info "Double tap to activate"
 */
internal fun View.changeClickActionInfo(label: String) {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val action = AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_CLICK,
                    label
                )
                info.addAction(action)
            }
        }
    )
}

/**
 * Validates whether given value is a valid mode for [View.setImportantForAccessibility] or
 * throws [IllegalArgumentException]
 */
internal fun validateImportantForAccessibilityOrThrow(mode: Int) {
    listOf(
        View.IMPORTANT_FOR_ACCESSIBILITY_YES,
        View.IMPORTANT_FOR_ACCESSIBILITY_NO,
        View.IMPORTANT_FOR_ACCESSIBILITY_AUTO,
        View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
    ).find { it == mode } ?: throw IllegalArgumentException(
        "Should be one of " +
            "View.IMPORTANT_FOR_ACCESSIBILITY_YES, " +
            "View.IMPORTANT_FOR_ACCESSIBILITY_NO, " +
            "View.IMPORTANT_FOR_ACCESSIBILITY_AUTO, " +
            "View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS"
    )
}

/**
 * Utility method to change focus to a new view. This is useful for components that become visible on user events such as Cards, Popups, Tooltips etc
 */
internal fun shiftFocus(view: View) {
    view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
}
