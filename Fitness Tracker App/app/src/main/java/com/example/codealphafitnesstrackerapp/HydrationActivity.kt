package com.example.codealphafitnesstrackerapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class HydrationActivity: AppCompatActivity() {
    private lateinit var backBtn: Button
    private lateinit var calcBtn: Button
    private lateinit var resultTextView: TextView
    private lateinit var weightInputLabel: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var weightInput: EditText
    private lateinit var progressBar: ProgressBar

    private var kg: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hydration)

        backBtn  = findViewById(R.id.backBtn)
        calcBtn= findViewById(R.id.calculateBtn)
        resultTextView = findViewById(R.id.resultTextview)
        weightInputLabel = findViewById(R.id.weightInputLabel)
        radioGroup = findViewById(R.id.radioGroup)
        weightInput = findViewById(R.id.weightInput)
        progressBar = findViewById(R.id.progressBar)


        calcBtn.setOnClickListener {
            if(!weightInput.text.isBlank()) {
                showLoading()
                Handler().postDelayed({
                    hideLoading()
                    calculate(kg)

                }, 500)

            }
            else{
                showToastNotif("Please Enter your weight")
            }
        }


        backBtn.setOnClickListener {
            redirectBack()
        }

        radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            val selectedRadioButton: RadioButton = findViewById(checkedId)


            val selectedOption: String = selectedRadioButton.text.toString()


            if(selectedOption.equals("Kilograms(Kg) and Millieters(mL)")){
                kg=true
                weightInput.hint ="Enter Your Weight (Kg)"
            }
            else{
                kg=false
                weightInput.hint ="Enter Your Weight (lbs)"
            }
        }


    }

    private fun calculate(kg: Boolean) {
        var weight=0
        var resHigh=0
        var resLow=0
        if(kg){
            weight= (weightInput.text.toString()).toLong().toInt()
            resHigh=  weight*35
            resLow= weight*30
            resultTextView.text = "$resLow - $resHigh mL Per Day"
        }
        else{
            weight= (weightInput.text.toString()).toLong().toInt()
            resHigh=  weight*1
            resLow= (weight*0.5).toInt()
            resultTextView.text = "$resLow - $resHigh Oz Per Day"

        }

        weightInput.text.clear()

    }

    private fun showToastNotif(message: String) {
        val context: Context = applicationContext
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun redirectBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }







    private fun showLoading() {
        backBtn.visibility = View.INVISIBLE
        calcBtn.visibility = View.INVISIBLE
        weightInput.visibility = View.INVISIBLE
        weightInputLabel.visibility = View.INVISIBLE
        resultTextView.visibility = View.INVISIBLE
        radioGroup.visibility = View.INVISIBLE

        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        backBtn.visibility = View.VISIBLE
        calcBtn.visibility = View.VISIBLE
        weightInput.visibility = View.VISIBLE
        weightInputLabel.visibility = View.VISIBLE
        resultTextView.visibility = View.VISIBLE
        radioGroup.visibility = View.VISIBLE

        progressBar.visibility = View.INVISIBLE
    }





}