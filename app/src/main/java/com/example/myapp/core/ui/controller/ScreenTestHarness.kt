package com.example.myapp.core.ui.controller

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi-Theme Preview Annotation for Screen Controllers.
 * Automatically renders both Light & Dark themes side-by-side in Android Studio preview.
 */
@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ThemePreviews

/**
 * Multi-Device & Orientation Preview Annotation for Screen Controllers.
 * Renders portrait phone, landscape phone, and tablet split-pane layouts.
 */
@Preview(name = "Phone Portrait", device = Devices.PHONE, showBackground = true)
@Preview(name = "Phone Landscape", device = "spec:width=891dp,height=411dp,orientation=landscape", showBackground = true)
@Preview(name = "Tablet 10-inch", device = Devices.TABLET, showBackground = true)
annotation class DeviceLayoutPreviews
