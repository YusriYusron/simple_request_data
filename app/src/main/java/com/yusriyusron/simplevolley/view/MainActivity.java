package com.yusriyusron.simplevolley.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yusriyusron.simplevolley.R;
import com.yusriyusron.simplevolley.api.WeatherAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView textViewCity;
    private TextView textViewHumadity;
    private TextView textViewTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCity = findViewById(R.id.text_view_city);
        textViewHumadity = findViewById(R.id.text_view_humadity);
        textViewTemperature = findViewById(R.id.text_view_temperature);


        requestWeather();
    }

    private void requestWeather(){
        String url = new WeatherAPI().getCurrentWeather("London","05cd291631057b58385f823d67237dcf");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONObject main = root.getJSONObject("main");
                    int temperature = main.getInt("temp");
                    int humidity = main.getInt("humidity");
                    String cityName = root.getString("name");

                    //Kelvin to Celcius
                    int celcius = temperature-273;

                    //Set JSON to UI
                    textViewCity.setText(cityName);
                    textViewHumadity.setText("Humidity "+humidity+"%");
                    textViewTemperature.setText(celcius+" C");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check your connectivity", Toast.LENGTH_SHORT).show();
                requestWeather();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
