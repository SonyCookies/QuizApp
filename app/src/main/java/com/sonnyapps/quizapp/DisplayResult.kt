package com.sonnyapps.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DisplayResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_result)

        val score = intent.getIntExtra("EXTRA_SCORE", 10)
        val tvScore = findViewById<TextView>(R.id.tv_score)
        val btnStartAgain = findViewById<Button>(R.id.btn_start_again)

        tvScore.text = score.toString()
        btnStartAgain.setOnClickListener{
            setResult(RESULT_OK)
            finish()
        }


    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }
}