package com.hpg.wordguesser.game

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin

class TimerSounds {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.Default)

    fun playWarningTick(secondsLeft: Int) {
        val frequencyHz = if (secondsLeft <= 3) 1174.7 else 880.0
        scope.launch { playTone(frequencyHz, durationMs = 70, amplitude = 0.36) }
    }

    fun playRoundEnd() {
        scope.launch {
            playTone(523.25, durationMs = 110, amplitude = 0.48)
            playTone(392.00, durationMs = 240, amplitude = 0.52)
        }
    }

    fun release() {
        job.cancel()
    }

    private suspend fun playTone(frequencyHz: Double, durationMs: Int, amplitude: Double) {
        val sampleRate = 22050
        val count = sampleRate * durationMs / 1000
        if (count <= 0) return
        val samples = ShortArray(count)
        val fadeIn = (sampleRate * 0.004).toInt().coerceAtLeast(1)
        val fadeOut = (sampleRate * 0.012).toInt().coerceAtLeast(1)
        for (i in 0 until count) {
            val angle = TWO_PI * i * frequencyHz / sampleRate
            var envelope = 1.0
            if (i < fadeIn) envelope = i.toDouble() / fadeIn
            val tail = count - 1 - i
            if (tail < fadeOut) envelope *= tail.toDouble() / fadeOut
            samples[i] = (sin(angle) * amplitude * envelope * Short.MAX_VALUE).toInt()
                .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
                .toShort()
        }
        val minBuffer = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        if (minBuffer <= 0) return
        val track = try {
            AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setSampleRate(sampleRate)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes((count * 2).coerceAtLeast(minBuffer))
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()
        } catch (_: RuntimeException) {
            return
        }
        try {
            track.write(samples, 0, samples.size)
            track.play()
            delay(durationMs.toLong() + 20)
        } catch (_: RuntimeException) {
        } finally {
            try {
                track.stop()
            } catch (_: RuntimeException) {
            }
            track.release()
        }
    }

    private companion object {
        const val TWO_PI = Math.PI * 2.0
    }
}
