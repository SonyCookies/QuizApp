package com.sonnyapps.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sonnyapps.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.btnStart.setOnClickListener {

            val etNameString = binding.etName.text.toString()

            when {
                etNameString.isEmpty() -> {
                    Toast.makeText(this, "Please enter name.", Toast.LENGTH_SHORT).show()
                }
                etNameString.count() < 3 -> {
                    Toast.makeText(this, "Please enter at least 3 characters.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent = Intent(this, QuizQuestionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }






        }

    }
}