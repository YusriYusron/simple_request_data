package com.yusriyusron.simplevolley.api;

import android.net.Uri;

public class WeatherAPI {
    public String getCurrentWeather(String cityName, String apiKey){
        String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
        return Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("q",cityName)
                .appendQueryParameter("appid",apiKey)
                .build()
                .toString();
    }
}
