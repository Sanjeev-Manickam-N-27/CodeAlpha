package com.example.codealphafitnesstrackerapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BPMActivity : AppCompatActivity() {
    private lateinit var backBtn: Button
    private lateinit var calculateBtn: Button
    private lateinit var resultText: TextView
    private lateinit var ageInputLabel: TextView
    private lateinit var ageInput : EditText
    private lateinit var progressBar: ProgressBar

    var BPM =0;
    var age=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bpm)


        backBtn= findViewById(R.id.backBtn)
        calculateBtn = findViewById(R.id.calculateBtn)
        resultText = findViewById(R.id.resultText)
        ageInput   = findViewById(R.id.ageInput)
        ageInputLabel = findViewById(R.id.ageInputLabel)
        progressBar = findViewById(R.id.progressBar)



        backBtn.setOnClickListener {
            back()
        }

        calculateBtn.setOnClickListener {

            showLoading()
            Handler().postDelayed({
                hideLoading()
                calculateBPM()

            }, 500)
        }
    }

    fun back(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun calculateBPM(){
        age = Integer.parseInt(ageInput.text.toString())

        BPM = 220 - age

        resultText.text = BPM.toString()

    }


private fun showLoading() {

//    textView.visibility = View.GONE
    backBtn.visibility = View.INVISIBLE
    ageInput.visibility = View.INVISIBLE
    ageInputLabel.visibility = View.INVISIBLE
    resultText.visibility = View.INVISIBLE

    progressBar.visibility = View.VISIBLE
}

private fun hideLoading() {

//    textView.visibility = View.VISIBLE
    backBtn.visibility = View.VISIBLE
    ageInput.visibility = View.VISIBLE
    ageInputLabel.visibility = View.VISIBLE
    calculateBtn.text = "Recalculate"
    resultText.visibility = View.VISIBLE

    progressBar.visibility = View.GONE
}
}


