package com.example.codealphafitnesstrackerapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryActivity : AppCompatActivity()   {
    private lateinit var backBtn: Button
    private lateinit var clear: Button
    private lateinit var historyText: TextView

//    var workouts = WorkoutActivity.workouts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        backBtn = findViewById(R.id.backBtn)
        clear = findViewById(R.id.clear)
        historyText = findViewById(R.id.historyText)

        backBtn.setOnClickListener {
            redirectBack()
        }

        clear.setOnClickListener {
            clear()
        }


        var workouts= retrieveWorkouts()

        historyText.text = getHistory()

    }



    fun saveWorkoutsListToSharedPreferences(workouts: MutableList<WorkoutActivity.Workout>) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        val jsonAdapter = Gson().getAdapter(object : TypeToken<MutableList<WorkoutActivity.Workout>>() {})
        val workoutsJson = jsonAdapter.toJson(workouts)

        editor.putString("workoutslist", workoutsJson)
        editor.apply()
    }

    fun retrieveWorkouts(): MutableList<WorkoutActivity.Workout> {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val workoutsJson = sharedPreferences.getString("workoutslist", "")


        return Gson().fromJson(
            workoutsJson,
            object : TypeToken<MutableList<WorkoutActivity.Workout>>() {}.type
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
    private fun redirectBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }

    fun clear(){
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("workoutslist")
        editor.apply()

        workouts.clear()
        historyText.text = ""

    }

    fun getHistory(): String {
        var concatenatedString = workouts.joinToString("\n\n") { workout ->
            "Name: ${workout.name} Duration: ${workout.duration} " +
                    "Date: ${workout.date} Time: ${workout.time}"
        }
        return concatenatedString
        }
    }



