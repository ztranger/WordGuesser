package com.hpg.wordguesser.game

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
        scope.launch { playSteamHorn() }
    }

    fun release() {
        job.cancel()
    }

    private suspend fun playTone(frequencyHz: Double, durationMs: Int, amplitude: Double) {
        val sampleRate = SAMPLE_RATE
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
        playPcm(samples, sampleRate, durationMs)
    }

    /**
     * Low steam-whistle blast: odd-heavy harmonics, slow swell, slight pitch scoop.
     * About twice as long as the old two-tone end cue (~350 ms).
     */
    private suspend fun playSteamHorn() {
        val sampleRate = SAMPLE_RATE
        val durationMs = 720
        val count = sampleRate * durationMs / 1000
        val samples = ShortArray(count)
        val attack = (sampleRate * 0.10).toInt()
        val release = (sampleRate * 0.20).toInt()
        val fundamental = 146.83
        var phase = 0.0
        for (i in 0 until count) {
            val t = i.toDouble() / sampleRate
            val pitch = if (i < attack) {
                0.93 + 0.07 * smoothstep(i.toDouble() / attack)
            } else {
                1.0
            }
            phase += TWO_PI * fundamental * pitch / sampleRate
            val wave = (
                0.50 * sin(phase) +
                    0.72 * sin(phase * 2) +
                    0.95 * sin(phase * 3) +
                    0.30 * sin(phase * 4) +
                    0.48 * sin(phase * 5.01) +
                    0.16 * sin(phase * 6) +
                    0.28 * sin(phase * 7)
                ) / 3.39
            var envelope = 1.0
            if (i < attack) envelope = smoothstep(i.toDouble() / attack)
            val tail = count - 1 - i
            if (tail < release) envelope *= smoothstep(tail.toDouble() / release)
            envelope *= 0.90 + 0.10 * sin(TWO_PI * 4.8 * t)
            samples[i] = (wave * 0.62 * envelope * Short.MAX_VALUE).toInt()
                .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
                .toShort()
        }
        playPcm(samples, sampleRate, durationMs)
    }

    private suspend fun playPcm(samples: ShortArray, sampleRate: Int, durationMs: Int) {
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
                .setBufferSizeInBytes((samples.size * 2).coerceAtLeast(minBuffer))
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
        const val SAMPLE_RATE = 22050
        const val TWO_PI = Math.PI * 2.0

        fun smoothstep(x: Double): Double {
            val t = x.coerceIn(0.0, 1.0)
            return t * t * (3.0 - 2.0 * t)
        }
    }
}
