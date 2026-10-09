package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopHelloBanner(
    totalTaps: Int,
    isRainMode: Boolean,
    isHapticEnabled: Boolean,
    isSoundEnabled: Boolean,
    onToggleRain: () -> Unit,
    onToggleHaptic: () -> Unit,
    onToggleSound: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("top_hello_banner"),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hello, World! 🌍✨",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.testTag("hello_world_title")
                )
                Text(
                    text = "Всего смайликов: $totalTaps",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.testTag("total_taps_counter")
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sound toggle button
                FilledTonalIconButton(
                    onClick = onToggleSound,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (isSoundEnabled) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSoundEnabled) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("sound_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isSoundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                        contentDescription = if (isSoundEnabled) "Звук включен" else "Звук выключен"
                    )
                }

                // Rain mode toggle
                FilledTonalIconButton(
                    onClick = onToggleRain,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (isRainMode) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isRainMode) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("rain_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isRainMode) Icons.Filled.WaterDrop else Icons.Outlined.WaterDrop,
                        contentDescription = "Режим дождя эмодзи"
                    )
                }

                // Haptic toggle
                FilledTonalIconButton(
                    onClick = onToggleHaptic,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (isHapticEnabled) MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isHapticEnabled) MaterialTheme.colorScheme.onSecondaryContainer
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("haptic_toggle_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Vibration,
                        contentDescription = "Вибрация при тапе"
                    )
                }
            }
        }
    }
}
