package io.github.smithjustinn84_netizen.rickandmortyapp.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A generic loading UI component.
 *
 * Callers should provide their own localized [label] if text is desired.
 */
@Composable
fun Loading(
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CircularProgressIndicator()
            if (label != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = label, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
