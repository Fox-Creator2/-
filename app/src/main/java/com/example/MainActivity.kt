package com.example

import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.EmojiScreen
import com.example.ui.EmojiViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val emojiViewModel: EmojiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Bind hardware volume keys to Media stream
        volumeControlStream = AudioManager.STREAM_MUSIC

        // Ensure stream volume is not 0 in emulator/device
        val audioManager = getSystemService(AUDIO_SERVICE) as? AudioManager
        audioManager?.let { am ->
            try {
                val cur = am.getStreamVolume(AudioManager.STREAM_MUSIC)
                val max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                if (cur == 0 && max > 0) {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, (max * 0.8f).toInt().coerceAtLeast(1), 0)
                }
            } catch (_: Exception) {
            }
        }

        setContent {
            MyApplicationTheme {
                EmojiScreen(viewModel = emojiViewModel)
            }
        }
    }
}
