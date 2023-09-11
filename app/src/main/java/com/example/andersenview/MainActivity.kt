package com.example.andersenview

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)

        val customClock3 = CustomClockView(this)
        val customClock3LayoutParams = FrameLayout.LayoutParams(800, 800)
        customClock3LayoutParams.apply {
            gravity = Gravity.CENTER
            setMargins(20, 20, 20, 20)
        }
        customClock3.apply {
            setPadding(5, 5, 5, 5)
            circleColor = Color.BLACK
            secondHandColor = Color.BLUE
            minutesHandColor = Color.RED

            layoutParams = customClock3LayoutParams
        }
        frameLayout.addView(customClock3)


    }
}