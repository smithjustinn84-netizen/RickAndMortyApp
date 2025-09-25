package io.github.smithjustinn84_netizen.rickandmortyapp.ui.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Composable function that displays a Snackbar notification.
 *
 * @param snackbarHostState The [SnackbarHostState] to show the Snackbar.
 * @param message The text to show in the Snackbar.
 * @param actionLabel The label for the action button (e.g., "Retry", "Dismiss").
 * @param duration The duration for which the Snackbar should be shown.
 * @param onAction Callback invoked when the action is clicked.
 * @param onDismiss Callback invoked when the Snackbar is dismissed (either by timeout or swipe).
 */
@Composable
fun Notification(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    onAction: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    // Re-launch the effect if message, actionLabel, or duration changes.
    // Also include snackbarHostState in case it could ever change, though less common.
    LaunchedEffect(snackbarHostState, message, actionLabel, duration) {
        // Ensure there's a message to display
        if (message.isNotEmpty()) {
            val result = snackbarHostState.showSnackbar(
                message = message,
                duration = duration,
                actionLabel = actionLabel,
                withDismissAction = duration == SnackbarDuration.Indefinite // Show dismiss for indefinite Snackbars
            )
            when (result) {
                SnackbarResult.Dismissed -> onDismiss()
                SnackbarResult.ActionPerformed -> onAction()
            }
        }
    }
}
