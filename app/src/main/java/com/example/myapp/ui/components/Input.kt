package com.example.myapp.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import java.util.*

enum class InputTypeField {
    TEXT,
    PASSWORD,
    EMAIL,
    DATE
}

@Composable
fun AppInput(
    value: String,
    onValueChange: (String) -> Unit,
    type: InputTypeField = InputTypeField.TEXT,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    textStyle: TextStyle = TextStyle.Default
) {
    when (type) {
        InputTypeField.DATE ->
            DateInputField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier.fillMaxWidth(),
                label = label,
                placeholder = placeholder,
                textStyle = textStyle
            )
        else ->
            TextInputField(
                value = value,
                onValueChange = onValueChange,
                type = type,
                modifier = modifier.fillMaxWidth(),
                label = label,
                placeholder = placeholder,
                textStyle = textStyle
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    type: InputTypeField,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    textStyle: TextStyle = TextStyle.Default
) {
    val visualTransformation =
        if (type == InputTypeField.PASSWORD) PasswordVisualTransformation()
        else VisualTransformation.None

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { if (label != null) Text(label) },
        placeholder = { if (placeholder != null) Text(placeholder) },
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    textStyle: TextStyle = TextStyle.Default
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val dateStr = "$year-${month + 1}-$dayOfMonth"
            onValueChange(dateStr)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = value,
        onValueChange = { }, // read-only
        readOnly = true,
        modifier = modifier.clickable { datePickerDialog.show() },
        label = { if (label != null) Text(label) },
        placeholder = { if (placeholder != null) Text(placeholder) },
        textStyle = textStyle,
        singleLine = true
    )
}
