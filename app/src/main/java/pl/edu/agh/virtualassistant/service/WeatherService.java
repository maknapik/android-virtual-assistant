package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.infrastructure.OpenWeatherClient;
import pl.edu.agh.virtualassistant.model.Weather;

public class WeatherService {

    private static final double KELVIN = 273.0;

    public static void  getTemperatureForCity(Context context, String cityName,
                                              Consumer<Double> onResponseCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName, getOnResponseCallback(onResponseCallback));
    }

    private static Consumer<Weather> getOnResponseCallback(Consumer<Double> onResponseCallback) {
        return weather -> {
            onResponseCallback.accept(weather.getMain().getTemp() - KELVIN);
        };
    }

    public static void  getTemperatureForCity(Context context, String cityName,
                                              Consumer<Double> onResponseCallback,Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName, getOnResponseCallback(onResponseCallback), onErrorCallback);
    }
}
