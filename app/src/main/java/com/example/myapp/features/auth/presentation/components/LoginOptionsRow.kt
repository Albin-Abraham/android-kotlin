package com.example.myapp.features.auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.theme.spacing

/**
 * Accessible Remember-Me and Forgot-Password options row.
 * Guarantees minimum touch target bounds and unified click semantics.
 */
@Composable
fun LoginOptionsRow(
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onRememberMeChange(!rememberMe) }
                .padding(vertical = spacing.extraSmall, horizontal = spacing.extraSmall)
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = onRememberMeChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(spacing.extraSmall))
            Text(
                text = "Remember me",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.heightIn(min = 48.dp)
        ) {
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
