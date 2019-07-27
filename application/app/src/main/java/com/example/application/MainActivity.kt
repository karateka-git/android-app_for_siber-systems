package com.example.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var weather: Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weather = Weather(this)
        setContentView(R.layout.activity_main)
        val actionDB = ActionDB(this)
        val cv = actionDB.read("tempInCity")
        if (cv != null) {
            weather.updateWeatherData(cv.getAsString("city"));
        }
    }

    fun saveCity(view: View) {
        val cityString = editText.text.toString();
        weather.updateWeatherData(cityString);
    }
}
