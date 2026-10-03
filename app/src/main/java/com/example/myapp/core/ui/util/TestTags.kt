package com.example.myapp.core.ui.util

/**
 * Standard semantic test tags for UI state verification & accessibility tests.
 */
object TestTags {
    // Scaffold & Layout Tags
    const val SCAFFOLD_TOP_BAR = "scaffold_top_bar"
    const val SCAFFOLD_BOTTOM_ACTION = "scaffold_bottom_action"
    const val SKELETON_LOADER = "skeleton_loader"

    // Form & Input Tags
    fun fieldInput(key: String): String = "field_input_$key"
    fun fieldError(key: String): String = "field_error_$key"
    const val SUBMIT_BUTTON = "submit_button"
    const val ERROR_BANNER = "error_banner"

    // Auth Screen Specific Tags
    const val REMEMBER_ME_CHECKBOX = "remember_me_checkbox"
    const val FORGOT_PASSWORD_BUTTON = "forgot_password_button"
    const val SIGN_UP_BUTTON = "sign_up_button"
    const val QUICK_DEMO_BUTTON = "quick_demo_button"
}
