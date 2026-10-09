package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.audio.SoundEffectManager
import com.example.ui.components.BottomActionBar
import com.example.ui.components.CategoryChipsRow
import com.example.ui.components.FloatingEmojiOverlay
import com.example.ui.components.RecentEmojisRow
import com.example.ui.components.SpotlightEmojiCard
import com.example.ui.components.TopHelloBanner
import kotlinx.coroutines.launch

@Composable
fun EmojiScreen(
    viewModel: EmojiViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val soundManager = remember(context) { SoundEffectManager(context) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    DisposableEffect(soundManager) {
        onDispose {
            soundManager.release()
        }
    }

    LaunchedEffect(uiState.snackbarMessage) {
        val msg = uiState.snackbarMessage
        if (msg != null) {
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.dismissSnackbar()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("emoji_main_screen"),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("emoji_snackbar_host")
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                // Pointer detector on the full screen surface to spawn emojis wherever tapped!
                .pointerInput(uiState.isHapticEnabled, uiState.isSoundEnabled, uiState.comboStreak) {
                    detectTapGestures { offset ->
                        if (uiState.isHapticEnabled) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }
                        // Short playful pop sound triggered whenever an emoji appears after a tap
                        soundManager.playPop(
                            enabled = uiState.isSoundEnabled,
                            comboStreak = uiState.comboStreak + 1
                        )
                        viewModel.onScreenTap(offset.x, offset.y)
                    }
                }
        ) {
            val screenWidthPx = with(density) { maxWidth.toPx() }
            val screenHeightPx = with(density) { maxHeight.toPx() }

            // Floating / bursting emoji particles overlay
            FloatingEmojiOverlay(
                particles = uiState.particles,
                modifier = Modifier.fillMaxSize()
            )

            // Content Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Centered constrained container for large screens/tablets
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 640.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Hello Banner
                    TopHelloBanner(
                        totalTaps = uiState.totalTapCount,
                        isRainMode = uiState.isRainMode,
                        isHapticEnabled = uiState.isHapticEnabled,
                        isSoundEnabled = uiState.isSoundEnabled,
                        onToggleRain = { viewModel.toggleRainMode(screenWidthPx) },
                        onToggleHaptic = { viewModel.toggleHaptic() },
                        onToggleSound = {
                            val nextState = !uiState.isSoundEnabled
                            viewModel.toggleSound()
                            if (nextState) {
                                soundManager.playPop(enabled = true, comboStreak = 1)
                                viewModel.showSnackbar("Звук включен 🔊 (проверьте громкость устройства)")
                            } else {
                                viewModel.showSnackbar("Звук выключен 🔇")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Category Filter Chips
                    CategoryChipsRow(
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = { category ->
                            viewModel.selectCategory(category)
                            if (uiState.isHapticEnabled) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }
                            soundManager.playPop(enabled = uiState.isSoundEnabled, comboStreak = 1)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Spotlight Big Emoji Card
                    SpotlightEmojiCard(
                        emojiItem = uiState.currentEmoji,
                        comboStreak = uiState.comboStreak,
                        onCopyClick = {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                            val clip = ClipData.newPlainText("Emoji", uiState.currentEmoji.emoji)
                            clipboard?.setPrimaryClip(clip)
                            viewModel.showSnackbar("Смайлик '${uiState.currentEmoji.emoji}' скопирован в буфер!")
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Recent Emojis Ribbon
                    RecentEmojisRow(
                        recentEmojis = uiState.recentEmojis,
                        currentEmoji = uiState.currentEmoji,
                        onEmojiClick = { item ->
                            viewModel.selectRecentEmoji(item, screenWidthPx / 2, screenHeightPx / 2)
                            if (uiState.isHapticEnabled) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }
                            soundManager.playPop(enabled = uiState.isSoundEnabled, comboStreak = 1)
                        }
                    )
                }

                // Bottom Action Bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 640.dp)
                        .padding(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomActionBar(
                        onBurstClick = {
                            if (uiState.isHapticEnabled) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                            soundManager.playChime(enabled = uiState.isSoundEnabled)
                            viewModel.triggerBurst(screenWidthPx / 2, screenHeightPx / 2)
                        },
                        onRandomMixClick = {
                            if (uiState.isHapticEnabled) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }
                            soundManager.playPop(
                                enabled = uiState.isSoundEnabled,
                                comboStreak = uiState.comboStreak + 1
                            )
                            viewModel.onScreenTap(screenWidthPx / 2, screenHeightPx / 2)
                        },
                        onClearClick = {
                            viewModel.clearParticles()
                        }
                    )
                }
            }
        }
    }
}
