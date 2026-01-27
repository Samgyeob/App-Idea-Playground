package com.example.pocketreset

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private var timer: CountDownTimer? = null

    private val focusTime = 25 * 60 * 1000L // 25 minutes
    private val breakTime = 5 * 60 * 1000L // 5 minutes
    private var isFocus = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText = findViewById(R.id.timerText)
        startButton = findViewById(R.id.startButton)

        updateTimerText(focusTime)

        startButton.setOnClickListener {
            startTimer()
        }
    }

    private fun startTimer() {
        timer?.cancel()

        val duration = if (isFocus) focusTime else breakTime

        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                isFocus = !isFocus
                startButton.text =
                    if (isFocus) "Start Focus" else "Start Reset"
                timerText.text =
                    if (isFocus) "Focus Time!" else "Reset Time!"
            }
        }.start()
    }

    private fun updateTimerText(ms: Long) {
        val minutes = (ms / 1000) / 60
        val seconds = (ms / 1000) % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }
}
