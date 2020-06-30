package fr.epita.android.covidata2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val totalsButton = findViewById<Button>(R.id.data)
            totalsButton.setOnClickListener{
            val intent = Intent(this, totals_view::class.java)
            startActivity(intent)
        }

        val graphButton = findViewById<Button>(R.id.graph)
            graphButton.setOnClickListener{
            val intent = Intent(this, graph_view::class.java)
            startActivity(intent)
        }

        val mysteryButton = findViewById<Button>(R.id.random)
            mysteryButton.setOnClickListener{
            val intent = Intent(this, mystery_view::class.java)
            startActivity(intent)
        }
    }
}