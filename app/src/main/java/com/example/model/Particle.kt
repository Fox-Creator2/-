package com.example.model

data class EmojiParticle(
    val id: Long,
    val emoji: String,
    val x: Float,
    val y: Float,
    val vx: Float,
    val vy: Float,
    val scale: Float,
    val rotation: Float,
    val vRot: Float,
    val alpha: Float = 1f,
    val ageMs: Long = 0L,
    val maxLifeMs: Long = 1800L
) {
    val isDead: Boolean get() = ageMs >= maxLifeMs

    fun nextState(deltaMs: Long): EmojiParticle {
        val newAge = ageMs + deltaMs
        val progress = (newAge.toFloat() / maxLifeMs).coerceIn(0f, 1f)
        // Gravity or upward buoyancy depending on vy
        val currentVy = vy + (250f * (deltaMs / 1000f)) // gentle gravity
        val currentX = x + vx * (deltaMs / 1000f)
        val currentY = y + currentVy * (deltaMs / 1000f)
        val currentRotation = rotation + vRot * (deltaMs / 1000f)
        val currentAlpha = (1f - progress).coerceIn(0f, 1f)
        val currentScale = (scale * (1f - progress * 0.3f)).coerceAtLeast(0.2f)

        return copy(
            x = currentX,
            y = currentY,
            vy = currentVy,
            rotation = currentRotation,
            alpha = currentAlpha,
            scale = currentScale,
            ageMs = newAge
        )
    }
}
