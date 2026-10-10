package com.example.myapp.core.navigation

enum class PresentationMode {
    STANDARD,
    MODAL_BOTTOM_SHEET,
    FULL_SCREEN_DIALOG,
    SPLIT_PANE_DETAIL
}

/**
 * Standardized Route Metadata Contract for ZenOS.
 * Feature modules declare their navigable destinations implementing [ZenOsRoute].
 */
interface ZenOsRoute {
    val routePattern: String
    val requiresAuthentication: Boolean get() = true
    val requiredPermissions: Set<String> get() = emptySet()
    val requiredFeatureFlag: String? get() = null
    val presentationMode: PresentationMode get() = PresentationMode.STANDARD
    val deepLinkUriPattern: String? get() = null
}
