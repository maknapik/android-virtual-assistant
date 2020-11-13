package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.infrastructure.OpenWeatherClient;
import pl.edu.agh.virtualassistant.model.Weather;

public class WeatherService {

    private static final double KELVIN = 273.0;

    public static void  getTemperatureForCity(Context context, String cityName,
                                              Consumer<Double> onResponseCallback) {
        Consumer<Weather> onResponseCallbackInternal = weather -> {
            onResponseCallback.accept(weather.getMain().getTemp() - KELVIN);
        };
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName, onResponseCallbackInternal);
    }

}
