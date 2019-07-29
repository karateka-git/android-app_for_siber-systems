package com.example.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class Weather {
    private Handler handler;
    private Context context;

    public Weather(Context context) {
        this.context = context;
        handler = new Handler();
    }
    public void updateWeatherData(final String city){
        final ActionDB actionDB = new ActionDB(context);
        new Thread(){
            public void run(){
                final JSONObject json = WeatherApi.getJSON(city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(context,
                                    R.string.place_not_found,
                                    Toast.LENGTH_LONG).show();
                            ContentValues cv = actionDB.read("tempInCity");
                            if (cv != null) {
                                renderWeather(cv.getAsDouble("temperature"), cv.getAsString("city"));
                            }
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            try {
                                JSONObject main = json.getJSONObject("main");
                                double temp = main.getDouble("temp");
                                renderWeather(temp, city);
                                actionDB.insert("tempInCity", city, temp);
                            } catch(Exception e){
                                Log.e("SimpleWeather", "Field not found in the JSON data");
                            }
                        }
                    });
                }

            }
        }.start();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void renderWeather(double temp, String city){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.textCity);
        textView.setText(String.format("%s", city));
        textView = (TextView) ((Activity)context).findViewById(R.id.textTemperature);
        textView.setText(String.format("%.2f ℃", temp));
    }
}
