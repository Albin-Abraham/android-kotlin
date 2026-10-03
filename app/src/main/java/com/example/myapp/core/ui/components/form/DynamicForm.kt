package com.example.myapp.core.ui.components.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapp.core.domain.form.FieldIconType
import com.example.myapp.core.domain.form.FormFieldDescriptor

/**
 * Config-driven Material 3 Form Renderer.
 * Interprets pure domain [FormFieldDescriptor] schemas and binds values/errors reactively.
 */
@Composable
fun DynamicForm(
    fields: List<FormFieldDescriptor>,
    formValues: Map<String, Any?>,
    fieldErrors: Map<String, String?>,
    onFieldValueChange: (key: String, value: Any?) -> Unit,
    modifier: Modifier = Modifier,
    onImeSubmit: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        fields.forEachIndexed { index, field ->
            val isLastField = index == fields.lastIndex

            when (field) {
                is FormFieldDescriptor.Text -> {
                    var passwordVisible by remember { mutableStateOf(false) }
                    val isPassword = field.isPassword
                    val error = fieldErrors[field.key]

                    OutlinedTextField(
                        value = (formValues[field.key] as? String).orEmpty(),
                        onValueChange = { onFieldValueChange(field.key, it) },
                        label = { Text(field.label) },
                        placeholder = { Text(field.placeholder) },
                        leadingIcon = getLeadingIcon(field.iconType),
                        isError = error != null,
                        supportingText = {
                            AnimatedVisibility(
                                visible = error != null,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                if (error != null) {
                                    Text(
                                        text = error,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        },
                        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = when {
                                isPassword -> KeyboardType.Password
                                field.iconType == FieldIconType.EMAIL -> KeyboardType.Email
                                field.iconType == FieldIconType.PHONE -> KeyboardType.Phone
                                else -> KeyboardType.Text
                            },
                            imeAction = if (isLastField) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            onDone = {
                                focusManager.clearFocus()
                                onImeSubmit?.invoke()
                            }
                        ),
                        trailingIcon = if (isPassword) {
                            {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                    )
                                }
                            }
                        } else null,
                        singleLine = field.maxLines == 1,
                        maxLines = field.maxLines,
                        shape = MaterialTheme.shapes.large,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is FormFieldDescriptor.Number -> {
                    val error = fieldErrors[field.key]
                    OutlinedTextField(
                        value = (formValues[field.key] as? String).orEmpty(),
                        onValueChange = { onFieldValueChange(field.key, it) },
                        label = { Text(field.label) },
                        isError = error != null,
                        supportingText = {
                            if (error != null) {
                                Text(text = error, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (isLastField) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            onDone = {
                                focusManager.clearFocus()
                                onImeSubmit?.invoke()
                            }
                        ),
                        singleLine = true,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is FormFieldDescriptor.Toggle -> {
                    val isChecked = (formValues[field.key] as? Boolean) ?: false
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = field.label,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            field.description?.let { desc ->
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Switch(
                            checked = isChecked,
                            onCheckedChange = { onFieldValueChange(field.key, it) }
                        )
                    }
                }

                is FormFieldDescriptor.Selection -> {
                    var expanded by remember { mutableStateOf(false) }
                    val selectedOption = (formValues[field.key] as? String).orEmpty()
                    val error = fieldErrors[field.key]

                    OptInSelectionDropdown(
                        field = field,
                        selectedOption = selectedOption,
                        error = error,
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        onOptionSelected = {
                            onFieldValueChange(field.key, it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun getLeadingIcon(type: FieldIconType): (@Composable () -> Unit)? {
    return when (type) {
        FieldIconType.PERSON -> { { Icon(Icons.Default.Person, contentDescription = null) } }
        FieldIconType.EMAIL -> { { Icon(Icons.Default.Email, contentDescription = null) } }
        FieldIconType.LOCK -> { { Icon(Icons.Default.Lock, contentDescription = null) } }
        FieldIconType.PHONE -> { { Icon(Icons.Default.Phone, contentDescription = null) } }
        FieldIconType.NONE, FieldIconType.NUMBER -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OptInSelectionDropdown(
    field: FormFieldDescriptor.Selection,
    selectedOption: String,
    error: String?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(field.label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            isError = error != null,
            supportingText = {
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            field.options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}
