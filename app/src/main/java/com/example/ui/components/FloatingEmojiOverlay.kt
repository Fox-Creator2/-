package com.example.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.model.EmojiParticle

@Composable
fun FloatingEmojiOverlay(
    particles: List<EmojiParticle>,
    modifier: Modifier = Modifier
) {
    val textPaint = remember {
        Paint().apply {
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        if (particles.isEmpty()) return@Canvas

        val baseSizePx = 44.dp.toPx()

        drawIntoCanvas { canvas ->
            val nativeCanvas = canvas.nativeCanvas
            for (p in particles) {
                nativeCanvas.save()
                nativeCanvas.translate(p.x, p.y)
                nativeCanvas.rotate(p.rotation)

                val scaledSize = baseSizePx * p.scale
                textPaint.textSize = scaledSize
                textPaint.alpha = (p.alpha * 255f).toInt().coerceIn(0, 255)

                // Vertically center emoji
                nativeCanvas.drawText(p.emoji, 0f, scaledSize * 0.35f, textPaint)
                nativeCanvas.restore()
            }
        }
    }
}
