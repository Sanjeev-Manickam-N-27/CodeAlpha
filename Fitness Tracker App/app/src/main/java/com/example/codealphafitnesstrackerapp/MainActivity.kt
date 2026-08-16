package com.example.codealphafitnesstrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.gtappdevelopers.kotlingfgproject.GridRVAdapter

class MainActivity : AppCompatActivity() {
    lateinit var menuGRV: GridView
    lateinit var menuList: List<GridViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        menuGRV = findViewById(R.id.idGRV)
        menuList = ArrayList<GridViewModel>()


        menuList = menuList + GridViewModel("Calculate Hydration", R.drawable.water ,
            HydrationActivity::class.java)
        menuList = menuList + GridViewModel("Calculate BPM", R.drawable.heart ,
            BPMActivity::class.java)
        menuList = menuList + GridViewModel("Meal Plan", R.drawable.meal,
            MealActivity::class.java)
        menuList = menuList + GridViewModel("Record OutDoor Run", R.drawable.running_boy ,
            RunActivity::class.java )
        menuList = menuList + GridViewModel("Record Workout", R.drawable.gym ,
            WorkoutActivity::class.java )
        menuList = menuList + GridViewModel("Workout History", R.drawable.dumbell ,
            HistoryActivity::class.java )


        val courseAdapter = GridRVAdapter(courseList = menuList, this@MainActivity)


        menuGRV.adapter = courseAdapter


        menuGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val targetActivityClass = menuList[position].targetActivity

            val intent = Intent(this@MainActivity, targetActivityClass)
            startActivity(intent)


             finish()
        }





    }
}