package com.example.filmstest.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class CustomSnackBarVisuals(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration,
    override val message: String = "",
    override val withDismissAction: Boolean,
    val onActionClick: () -> Unit,
    val messageRes: Int,
    val actionLabelRes: Int,
) : SnackbarVisuals

@Composable
fun CustomSnackBar(
    message: String,
    actionText: String?,
    modifier: Modifier = Modifier,
    onActionClick: (() -> Unit)? = null,
) {
    Snackbar(
        modifier = modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
            ),
        action = if (onActionClick == null || actionText == null) null else {
            {
                TextButton(
                    onClick = { onActionClick.invoke() },
                ) {
                    Text(
                        text = actionText,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            }
        },
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}
