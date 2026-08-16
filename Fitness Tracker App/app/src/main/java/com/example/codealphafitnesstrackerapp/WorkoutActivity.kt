package com.example.codealphafitnesstrackerapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale



var workouts=mutableListOf<WorkoutActivity.Workout>()
class WorkoutActivity  : AppCompatActivity()  {

    private var isRunning = false
    private var elapsedTime: Long = 0
    private var startTime: Long = 0
    var maxDuration: String = "00:00"
    var latestRun: String = "00:00"

    private lateinit var stopwatchTextView: TextView
    private lateinit var workoutInput: EditText
    private lateinit var backBtn: Button
    private lateinit var handler: Handler
    private lateinit var startBtn: Button
    private lateinit var stopBtn:  Button
    private lateinit var resetBtn: Button
    private lateinit var saveBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        handler = Handler()


        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)
        resetBtn = findViewById(R.id.resetBtn)
        backBtn = findViewById(R.id.backBtn)
        saveBtn = findViewById(R.id.saveBtn)
        stopwatchTextView = findViewById(R.id.stopwatchTextView)
        workoutInput = findViewById(R.id.workoutInput)

        startBtn.setOnClickListener {
            startStopwatch()
        }

        stopBtn.setOnClickListener {
            stopStopwatch()
        }

        resetBtn.setOnClickListener {
            resetStopwatch()
        }

        backBtn.setOnClickListener {
            redirectBack()
        }

        saveBtn.setOnClickListener {
            saveWorkout()
        }

        workouts = retrieveWorkouts()

    }

    private fun saveWorkout() {
        if(workoutInput.text.isBlank()){
            showToastNotif("Please Enter a workout name")
        }
        else{
            var name= workoutInput.text.toString()
            var time= stopwatchTextView.text.toString()
            val currentDate = Calendar.getInstance().time
            val currentTimestamp: Long = System.currentTimeMillis()

            val newWorkout = Workout(name, time, currentDate , currentTimestamp)

            workouts.add(newWorkout)

            saveWorkoutsListToSharedPreferences(workouts)

            workouts= retrieveWorkouts()

            showToastNotif("Workout Successfully Saved")
            workoutInput.text.clear()

            Log.d("MYLIST",workouts.toString())
        }
    }

    public class Workout(
        val name: String, val duration: String, val date: Date, val time:
        Long
    ){
        override fun toString(): String {
            return "Workout(Name='$name', duration='$duration' , date=$date , time=$time)"
        }
    }

//    companion object {

        fun saveWorkoutsListToSharedPreferences(workouts: MutableList<Workout>) {
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()


            val jsonAdapter = Gson().getAdapter(object : TypeToken<MutableList<Workout>>() {})
            val workoutsJson = jsonAdapter.toJson(workouts)

            editor.putString("workoutslist", workoutsJson)
            editor.apply()
        }

        fun retrieveWorkouts(): MutableList<Workout> {
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            val workoutsJson = sharedPreferences.getString("workoutslist", "")


            return Gson().fromJson(
                workoutsJson,
                object : TypeToken<MutableList<Workout>>() {}.type
            )
                ?: mutableListOf()
        }

        private fun retrieveSavedText(): String {
            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            val workoutsJson = sharedPreferences.getString("workoutslist", "")


            val savedWorkouts: MutableList<WorkoutActivity.Workout> =
                Gson().fromJson(
                    workoutsJson,
                    object : TypeToken<MutableList<WorkoutActivity.Workout>>() {}.type
                )
                    ?: mutableListOf()


            return savedWorkouts.joinToString("\n\n") {
                "Name:${it.name} Duration:${it.duration}  " +
                        "Date: ${it.date} Time:${it.time}"
            }
        }

//    }


    private fun redirectBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

//        fun clear(){
//            val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//
//            editor.remove("workoutslist")
//            editor.apply()
//
//            workouts.clear()
//
//            Log.d("MYLIST",workouts.toString())
//        }
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
            startBtn.isEnabled = false
            stopBtn.isEnabled = true
            resetBtn.isEnabled = false
            saveBtn.isEnabled = false
            stopBtn.setTextColor(Color.RED)


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
            startBtn.isEnabled = true
            stopBtn.isEnabled = false
            resetBtn.isEnabled = true
            saveBtn.isEnabled = true
            stopBtn.setTextColor(Color.BLACK)

            handler.removeCallbacks(runnable)

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

        stopwatchTextView.text = formattedTime
    }

    private fun showToastNotif(message: String) {
        val context: Context = applicationContext
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }





}