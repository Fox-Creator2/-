package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.SoundPool
import com.example.R
import kotlin.random.Random

class SoundEffectManager(context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private var popSoundId: Int = 0
    private var chimeSoundId: Int = 0
    private var isLoaded: Boolean = false

    private val popPcm: ShortArray = generatePopPcm()
    private val audioTrackPool: List<AudioTrack?> = (0 until 3).map {
        createStaticAudioTrack(popPcm)
    }
    private var nextTrackIdx = 0

    init {
        ensureAudibleVolume()
        try {
            popSoundId = soundPool.load(context, R.raw.emoji_pop, 1)
            chimeSoundId = soundPool.load(context, R.raw.emoji_chime, 1)
            soundPool.setOnLoadCompleteListener { _, _, status ->
                if (status == 0) {
                    isLoaded = true
                }
            }
        } catch (_: Exception) {
            isLoaded = false
        }
    }

    private fun ensureAudibleVolume() {
        try {
            audioManager?.let { am ->
                val currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC)
                val maxVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                if (currentVol == 0 && maxVol > 0) {
                    am.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        (maxVol * 0.8f).toInt().coerceAtLeast(1),
                        0
                    )
                }
            }
        } catch (_: Exception) {
        }
    }

    /**
     * Plays a cute short pop sound effect when an emoji appears after a tap.
     * Uses SoundPool as primary and AudioTrack as instant fallback.
     */
    fun playPop(enabled: Boolean, comboStreak: Int = 1) {
        if (!enabled) return

        ensureAudibleVolume()

        // Dynamic pitch modulation based on combo
        val basePitch = 1.0f + ((comboStreak.coerceAtMost(10) - 1) * 0.04f)
        val jitter = Random.nextFloat() * 0.12f - 0.06f
        val pitch = (basePitch + jitter).coerceIn(0.85f, 1.55f)

        var streamId = 0
        if (isLoaded && popSoundId != 0) {
            try {
                streamId = soundPool.play(popSoundId, 1.0f, 1.0f, 1, 0, pitch)
            } catch (_: Exception) {
                streamId = 0
            }
        }

        // If SoundPool didn't play (e.g. still loading or stream issue), use AudioTrack
        if (streamId == 0) {
            playAudioTrackFallback(pitch)
        }
    }

    /**
     * Plays a celebratory chime sound.
     */
    fun playChime(enabled: Boolean) {
        if (!enabled) return
        ensureAudibleVolume()

        var streamId = 0
        if (isLoaded && chimeSoundId != 0) {
            try {
                streamId = soundPool.play(chimeSoundId, 1.0f, 1.0f, 2, 0, 1.0f)
            } catch (_: Exception) {
                streamId = 0
            }
        }

        if (streamId == 0) {
            playAudioTrackFallback(1.3f)
        }
    }

    private fun playAudioTrackFallback(pitch: Float) {
        try {
            val track = audioTrackPool[nextTrackIdx % audioTrackPool.size]
            nextTrackIdx++
            track?.let {
                it.stop()
                val targetSampleRate = (44100 * pitch).toInt().coerceIn(22050, 88200)
                it.playbackRate = targetSampleRate
                it.reloadStaticData()
                it.play()
            }
        } catch (_: Exception) {
        }
    }

    private fun createStaticAudioTrack(pcmData: ShortArray): AudioTrack? {
        return try {
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(pcmData.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()
            track.write(pcmData, 0, pcmData.size)
            track
        } catch (_: Exception) {
            null
        }
    }

    private fun generatePopPcm(): ShortArray {
        val sampleRate = 44100
        val duration = 0.10f
        val numSamples = (sampleRate * duration).toInt()
        val pcm = ShortArray(numSamples)
        var phase = 0.0
        for (i in 0 until numSamples) {
            val t = i.toFloat() / sampleRate
            val normT = t / duration
            val freq = 380.0 + 440.0 * (1.0 - Math.exp(-normT * 28.0))
            phase += 2.0 * Math.PI * freq / sampleRate
            val env = if (t < 0.003f) {
                t / 0.003f
            } else {
                Math.exp(-32.0 * (t - 0.003f)).toFloat()
            }
            val sample = (Math.sin(phase) * env * 0.92f).coerceIn(-1.0, 1.0)
            pcm[i] = (sample * 32767.0).toInt().toShort()
        }
        return pcm
    }

    fun release() {
        try {
            soundPool.release()
        } catch (_: Exception) {
        }
        audioTrackPool.forEach { track ->
            try {
                track?.stop()
                track?.release()
            } catch (_: Exception) {
            }
        }
    }
}
