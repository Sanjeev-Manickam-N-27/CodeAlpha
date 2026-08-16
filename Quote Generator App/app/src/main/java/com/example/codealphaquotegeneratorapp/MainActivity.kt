package com.example.codealphaquotegeneratorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var quotesTV: TextView
    private lateinit var authorTV: TextView
    private lateinit var generateBtn: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var randomQuote: String
    private lateinit var randomAuthor: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        generateBtn= findViewById(R.id.generateButton)
        quotesTV= findViewById(R.id.quote)
        authorTV= findViewById(R.id.author)
        progressBar = findViewById(R.id.progressBar)

        generateBtn.setOnClickListener{
            generateQuote()
        }

        generateQuote()
    }


    fun generateQuote(){
        toggleProgressBar(true)
        GlobalScope.launch {
            try {
                val response = Retrofit.RandomQuoteApi.getRandomQuote()

              runOnUiThread {
               response.body()?.first()?.let{
                   toggleProgressBar(false)
                   randomQuote = response.body()!!.first().q
                   randomAuthor= response.body()!!.first().a
                   applyUI(randomQuote,randomAuthor)
                   }
               }
            }
            catch(e: Exception){
                runOnUiThread {
                    toggleProgressBar(false)
                    Toast.makeText(applicationContext, "Error occurred", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun applyUI(quote: String,author: String ){
        quotesTV.text = quote
        authorTV.text = author
    }

    fun toggleProgressBar(inProgress: Boolean){
        if(inProgress){
            progressBar.visibility = View.VISIBLE
            quotesTV.visibility= View.INVISIBLE
            authorTV.visibility= View.INVISIBLE
            generateBtn.isEnabled= false
        }
        else{
            progressBar.visibility = View.INVISIBLE
            quotesTV.visibility= View.VISIBLE
            authorTV.visibility= View.VISIBLE
            generateBtn.isEnabled= true
        }
    }


}