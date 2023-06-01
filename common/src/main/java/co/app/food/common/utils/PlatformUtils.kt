package co.app.food.common.utils

import android.os.Build

fun isLollipopOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun isMarshmallowOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun isOreoOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun isAbove(versionCode: Int) = Build.VERSION.SDK_INT > versionCode

fun isBetween(versionRange: IntRange) = Build.VERSION.SDK_INT in versionRange
