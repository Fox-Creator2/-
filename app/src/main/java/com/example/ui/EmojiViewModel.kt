package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.EmojiCategory
import com.example.model.EmojiItem
import com.example.model.EmojiParticle
import com.example.model.EmojiRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class EmojiUiState(
    val currentEmoji: EmojiItem = EmojiRepository.defaultEmoji,
    val selectedCategory: EmojiCategory = EmojiCategory.ALL,
    val totalTapCount: Int = 0,
    val comboStreak: Int = 0,
    val isRainMode: Boolean = false,
    val isHapticEnabled: Boolean = true,
    val isSoundEnabled: Boolean = true,
    val recentEmojis: List<EmojiItem> = listOf(EmojiRepository.defaultEmoji),
    val particles: List<EmojiParticle> = emptyList(),
    val snackbarMessage: String? = null
)

class EmojiViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EmojiUiState())
    val uiState: StateFlow<EmojiUiState> = _uiState.asStateFlow()

    private var lastTapTimestamp: Long = 0L
    private var particleIdCounter = 0L
    private var physicsJob: Job? = null
    private var rainJob: Job? = null

    init {
        startPhysicsEngine()
    }

    private fun startPhysicsEngine() {
        physicsJob?.cancel()
        physicsJob = viewModelScope.launch {
            var lastTime = System.currentTimeMillis()
            while (isActive) {
                val now = System.currentTimeMillis()
                val delta = (now - lastTime).coerceIn(1L, 50L)
                lastTime = now

                // Check combo timeout (1200ms)
                if (_uiState.value.comboStreak > 0 && (now - lastTapTimestamp > 1200L)) {
                    _uiState.update { it.copy(comboStreak = 0) }
                }

                // Update particles
                _uiState.update { state ->
                    if (state.particles.isEmpty()) {
                        state
                    } else {
                        val updated = state.particles
                            .map { it.nextState(delta) }
                            .filter { !it.isDead }
                        state.copy(particles = updated)
                    }
                }

                delay(16L) // ~60 FPS update rate
            }
        }
    }

    fun onScreenTap(x: Float, y: Float) {
        val now = System.currentTimeMillis()
        val isCombo = (now - lastTapTimestamp) <= 1000L
        val newCombo = if (isCombo) _uiState.value.comboStreak + 1 else 1
        lastTapTimestamp = now

        val newEmoji = EmojiRepository.getRandomEmoji(_uiState.value.selectedCategory)
        val particleCount = if (newCombo >= 5) 5 else if (newCombo >= 3) 4 else 3

        val newParticles = mutableListOf<EmojiParticle>()
        for (i in 0 until particleCount) {
            val angle = Random.nextDouble(0.0, Math.PI * 2)
            val speed = Random.nextFloat() * 400f + 250f
            val vx = (cos(angle) * speed).toFloat()
            val vy = (sin(angle) * speed - 200f).toFloat() // upward bias
            val scale = Random.nextFloat() * 0.6f + 0.9f
            val rot = Random.nextFloat() * 360f
            val vRot = (Random.nextFloat() * 360f - 180f)

            newParticles.add(
                EmojiParticle(
                    id = ++particleIdCounter,
                    emoji = if (i == 0) newEmoji.emoji else EmojiRepository.getRandomEmoji(_uiState.value.selectedCategory).emoji,
                    x = x,
                    y = y,
                    vx = vx,
                    vy = vy,
                    scale = scale,
                    rotation = rot,
                    vRot = vRot,
                    maxLifeMs = Random.nextLong(1200L, 2000L)
                )
            )
        }

        _uiState.update { state ->
            val updatedRecent = (listOf(newEmoji) + state.recentEmojis)
                .distinctBy { it.emoji }
                .take(12)

            state.copy(
                currentEmoji = newEmoji,
                totalTapCount = state.totalTapCount + 1,
                comboStreak = newCombo,
                recentEmojis = updatedRecent,
                particles = (state.particles + newParticles).takeLast(60)
            )
        }
    }

    fun triggerBurst(centerX: Float, centerY: Float) {
        val count = 24
        val burstParticles = mutableListOf<EmojiParticle>()
        val featuredEmoji = EmojiRepository.getRandomEmoji(_uiState.value.selectedCategory)

        for (i in 0 until count) {
            val angle = (Math.PI * 2 / count) * i + Random.nextDouble(-0.1, 0.1)
            val speed = Random.nextFloat() * 500f + 350f
            val vx = (cos(angle) * speed).toFloat()
            val vy = (sin(angle) * speed - 150f).toFloat()

            burstParticles.add(
                EmojiParticle(
                    id = ++particleIdCounter,
                    emoji = EmojiRepository.getRandomEmoji(_uiState.value.selectedCategory).emoji,
                    x = centerX,
                    y = centerY,
                    vx = vx,
                    vy = vy,
                    scale = Random.nextFloat() * 0.7f + 0.9f,
                    rotation = Random.nextFloat() * 360f,
                    vRot = (Random.nextFloat() * 400f - 200f),
                    maxLifeMs = Random.nextLong(1500L, 2500L)
                )
            )
        }

        _uiState.update { state ->
            val updatedRecent = (listOf(featuredEmoji) + state.recentEmojis)
                .distinctBy { it.emoji }
                .take(12)

            state.copy(
                currentEmoji = featuredEmoji,
                totalTapCount = state.totalTapCount + 1,
                comboStreak = state.comboStreak + 5,
                recentEmojis = updatedRecent,
                particles = (state.particles + burstParticles).takeLast(80)
            )
        }
    }

    fun toggleRainMode(screenWidth: Float = 1000f) {
        val nextMode = !_uiState.value.isRainMode
        _uiState.update { it.copy(isRainMode = nextMode) }

        if (nextMode) {
            rainJob?.cancel()
            rainJob = viewModelScope.launch {
                while (isActive && _uiState.value.isRainMode) {
                    val spawnX = Random.nextFloat() * screenWidth
                    val randomItem = EmojiRepository.getRandomEmoji(_uiState.value.selectedCategory)
                    val particle = EmojiParticle(
                        id = ++particleIdCounter,
                        emoji = randomItem.emoji,
                        x = spawnX,
                        y = -50f,
                        vx = (Random.nextFloat() * 80f - 40f),
                        vy = (Random.nextFloat() * 200f + 250f),
                        scale = Random.nextFloat() * 0.5f + 0.8f,
                        rotation = Random.nextFloat() * 40f - 20f,
                        vRot = Random.nextFloat() * 60f - 30f,
                        maxLifeMs = 3500L
                    )

                    _uiState.update { state ->
                        state.copy(particles = (state.particles + particle).takeLast(60))
                    }
                    delay(Random.nextLong(150L, 350L))
                }
            }
        } else {
            rainJob?.cancel()
            rainJob = null
        }
    }

    fun selectCategory(category: EmojiCategory) {
        val newEmoji = EmojiRepository.getRandomEmoji(category)
        _uiState.update {
            it.copy(
                selectedCategory = category,
                currentEmoji = newEmoji
            )
        }
    }

    fun selectRecentEmoji(item: EmojiItem, x: Float, y: Float) {
        val particle = EmojiParticle(
            id = ++particleIdCounter,
            emoji = item.emoji,
            x = x,
            y = y,
            vx = 0f,
            vy = -300f,
            scale = 1.4f,
            rotation = 0f,
            vRot = 45f,
            maxLifeMs = 1500L
        )

        _uiState.update { state ->
            state.copy(
                currentEmoji = item,
                totalTapCount = state.totalTapCount + 1,
                particles = (state.particles + particle).takeLast(60)
            )
        }
    }

    fun toggleHaptic() {
        _uiState.update { it.copy(isHapticEnabled = !it.isHapticEnabled) }
    }

    fun toggleSound() {
        _uiState.update { it.copy(isSoundEnabled = !it.isSoundEnabled) }
    }

    fun clearParticles() {
        _uiState.update { it.copy(particles = emptyList()) }
    }

    fun showSnackbar(message: String) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun dismissSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
