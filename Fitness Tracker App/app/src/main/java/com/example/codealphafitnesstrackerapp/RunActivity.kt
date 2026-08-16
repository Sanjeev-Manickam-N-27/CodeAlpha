package com.example.codealphafitnesstrackerapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

var runString= ""
class RunActivity  : AppCompatActivity() {
    private var isRunning = false
    private var elapsedTime: Long = 0
    private var startTime: Long = 0
    var maxDuration: String = "00:00"
    var latestRun: String = "00:00"

    private lateinit var handler: Handler
    private lateinit var btnStart: Button
    private lateinit var btnStop:  Button
    private lateinit var btnReset: Button
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var timeTextview: TextView
    private lateinit var bestRunTextview: TextView
    private lateinit var latestRunTextview: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)

        handler = Handler()


        timeTextview = findViewById(R.id.timeTextView)
        bestRunTextview = findViewById(R.id.bestRun)
        latestRunTextview = findViewById(R.id.latestRun)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnReset = findViewById(R.id.btnReset)
        btnBack = findViewById(R.id.btnBack)

        btnStart.setOnClickListener {
            startStopwatch()
        }

        btnStop.setOnClickListener {
            stopStopwatch()
        }

        btnReset.setOnClickListener {
            resetStopwatch()
        }

        btnBack.setOnClickListener {
            redirectBack()
        }
//        maxDuration = retrieveMaxDuration()
        bestRunTextview.text = retrieveMaxDuration()
        latestRunTextview.text = retrieveLatestRun()
    }

    private fun redirectBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private val runnable = object : Runnable {
        override fun run() {
            updateUI()
            handler.postDelayed(this, 1000)
        }
    }

    private fun startStopwatch() {
        if (!isRunning) {
            isRunning = true
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnReset.isEnabled = false
            btnStop.setTextColor(Color.RED)


            startTime = SystemClock.elapsedRealtime() - elapsedTime
            handler.post(runnable)
        } else {
            startTime = SystemClock.elapsedRealtime() - elapsedTime
            handler.post(runnable)
        }
    }

    private fun stopStopwatch() {
        if (isRunning) {
            isRunning = false
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnReset.isEnabled = true
            btnStop.setTextColor(Color.BLACK)

            handler.removeCallbacks(runnable)

            val currTime = convert(timeTextview.text.toString()).timeInMillis
            val maxDurTime = convert(maxDuration).timeInMillis

            if (currTime > maxDurTime) {
                maxDuration = timeTextview.text.toString()
                saveMaxDuration(maxDuration)
                bestRunTextview.text = maxDuration
                showToastNotif("New Record Reached!")
            }
            latestRun = timeTextview.text.toString()
            saveLatestRun(latestRun)
            latestRunTextview.text = latestRun

        }
    }

    private fun resetStopwatch() {
        if (!isRunning) {
            elapsedTime = 0
            startTime = SystemClock.elapsedRealtime()
            updateUI()
        }
    }

    private fun updateUI() {
        val time = SystemClock.elapsedRealtime() - startTime
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())
        val formattedTime = sdf.format(Date(time))

        timeTextview.text = formattedTime
    }

    private fun retrieveMaxDuration(): String {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getString("maxDuration", "") ?: ""
    }

    private fun saveMaxDuration(text: String) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("maxDuration", text)
        editor.apply()
    }

    private fun retrieveLatestRun(): String {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getString("latestRun", "") ?: ""
    }

    private fun saveLatestRun(text: String) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("latestRun", text)
        editor.apply()
    }

    fun convert(currTime: String): Calendar {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = dateFormat.parse(currTime)
        val calendar = Calendar.getInstance()

        try {
            calendar.time = date
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return calendar
    }



    private fun showToastNotif(message: String) {
        val context: Context = applicationContext
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
