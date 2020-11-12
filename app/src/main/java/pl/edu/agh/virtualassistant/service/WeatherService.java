package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.infrastructure.HttpClient;
import pl.edu.agh.virtualassistant.model.ResponseWeather;

public class WeatherService {

    private static final String API_KEY = "bf540f685b579cf130b18b50941b4459";
    private static final String API_ENDPOINT = "http://api.openweathermap.org/data/2.5/";

    public static void getCurrentWeatherForCity(Context context, String cityName) {
        String url = API_ENDPOINT + "weather?q=" + cityName + getApiKeySuffix();
        Consumer<JSONObject> onResponseCallback = jsonObject -> {
            JSON.parseObject(String.valueOf(jsonObject), ResponseWeather.class);
        };
        Consumer<VolleyError> onErrorResponseCallback = jsonObject -> {
            System.out.println(jsonObject);
        };

        HttpClient.sendRequest(context, Request.Method.GET, url, null, onResponseCallback, onErrorResponseCallback);
    }

    private static String getApiKeySuffix() {
        return "&appid=" + API_KEY;
    }

}
