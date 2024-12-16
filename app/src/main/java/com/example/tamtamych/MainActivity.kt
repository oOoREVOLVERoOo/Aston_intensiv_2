package com.example.tamtamych

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drumView: DrumView = findViewById(R.id.drumView)
        val buttonRotate: Button = findViewById(R.id.spinButton)
        val imageResult: ImageView = findViewById(R.id.imageResult)
        val textDisplayView: TextDisplayView = findViewById(R.id.textDisplayView)
        val resetButton: Button = findViewById(R.id.resetButton)
        val sizeSeekBar: SeekBar = findViewById(R.id.sizeSeekBar)

        sizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val scale = progress / 100f + 0.5f
                drumView.drumScale = scale
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        imageResult.load("https://via.assets.so/game.png?id=1&q=95&w=360&h=360&fit=fill") {
            crossfade(true)
            placeholder(android.R.color.darker_gray)
        }
        imageResult.visibility = android.view.View.GONE
        textDisplayView.visibility = android.view.View.GONE

        buttonRotate.setOnClickListener {
            buttonRotate.isEnabled = false
            val rand = Random.nextInt(1, 360).toFloat()
            drumView.spining = 540f + rand
            drumView.spinDrum(drumView.spining, onAnimationEnd = {
                val color = drumView.getColorAtTwelve()

                when (color) {
                    "Оранжевый", "Зеленый", "Синий" -> {
                        imageResult.isVisible = true
                        textDisplayView.isVisible = false
                    }
                    "Красный" -> {
                        textDisplayView.setTextAndColor("Красный", 0xFFFF0000.toInt())
                        textDisplayView.isVisible = true
                        imageResult.isVisible = false
                    }
                    "Желтый" -> {
                        textDisplayView.setTextAndColor("Желтый", 0xFFFFFF00.toInt())
                        textDisplayView.isVisible = true
                        imageResult.isVisible = false
                    }
                    "Голубой" -> {
                        textDisplayView.setTextAndColor("Голубой", 0xFF00FFFF.toInt())
                        textDisplayView.isVisible = true
                        imageResult.isVisible = false
                    }
                    "Фиолетовый" -> {
                        textDisplayView.setTextAndColor("Фиолетовый", 0xFF800080.toInt())
                        textDisplayView.isVisible = true
                        imageResult.isVisible = false
                    }
                    else -> {
                        imageResult.isVisible = false
                        textDisplayView.isVisible = false
                    }
                }
                buttonRotate.isEnabled = true
            })
        }
        resetButton.setOnClickListener {
            imageResult.isVisible = false
            textDisplayView.isVisible = false
            sizeSeekBar.progress = 50
            drumView.drumScale = 1.0f
        }
    }
}