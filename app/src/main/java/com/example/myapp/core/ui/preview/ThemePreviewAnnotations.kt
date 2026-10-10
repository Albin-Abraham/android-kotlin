package com.example.myapp.core.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi-Theme Compose Preview Annotation.
 * Renders any Composable across Light, Dark, and High Accessibility Font Scale (1.5x) simultaneously.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@Preview(
    name = "1. Phone - Light Mode",
    group = "Theme & Accessibility",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "2. Phone - Dark Mode",
    group = "Theme & Accessibility",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "3. Accessibility - Large Font (1.5x)",
    group = "Theme & Accessibility",
    fontScale = 1.5f,
    showBackground = true
)
annotation class ZenOsPreview

/**
 * Multi-Device Form-Factor Compose Preview Annotation.
 * Previews layouts across Phone Portrait, Phone Landscape, Foldable, and Tablet screen sizes.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@Preview(
    name = "Phone - Portrait",
    group = "Form Factors",
    device = "spec:width=411dp,height=891dp",
    showBackground = true
)
@Preview(
    name = "Foldable / Landscape",
    group = "Form Factors",
    device = "spec:width=673dp,height=841dp",
    showBackground = true
)
@Preview(
    name = "Tablet - Expanded (1280x800)",
    group = "Form Factors",
    device = "spec:width=1280dp,height=800dp,dpi=240",
    showBackground = true
)
annotation class ZenOsDevicePreviews
